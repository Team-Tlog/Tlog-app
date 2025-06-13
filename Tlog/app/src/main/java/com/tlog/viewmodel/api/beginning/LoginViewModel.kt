package com.tlog.viewmodel.beginning.login

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.tlog.api.FcmTokenBody
import com.tlog.api.FirebaseTokenData
import com.tlog.api.LoginApi
import com.tlog.data.api.BaseResponse
import kotlinx.coroutines.launch
import com.tlog.data.api.LoginRequest
import com.tlog.data.local.UserPreferences
import com.tlog.viewmodel.api.beginning.KakaoLoginManager
import com.tlog.viewmodel.api.beginning.NaverLoginManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import retrofit2.Response

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userPreferences: UserPreferences,
    private val loginApi: LoginApi
) : ViewModel() {
    // Kakao Manager 사용
    fun kakaoLogin(context: Context, navController: NavController) {
        KakaoLoginManager(context) { token ->
            loginToServer("KAKAO", token, navController)
        }.login()
    }

    // Naver Manager 사용
    fun naverLogin(context: Context, navController: NavController) {
        NaverLoginManager(context) { token ->
            loginToServer("NAVER", token, navController)
        }.login()
    }

    fun googleLogin(resultData: Intent?, navController: NavController) {
        try {
            val account = GoogleSignIn.getSignedInAccountFromIntent(resultData).getResult(
                ApiException::class.java)
            val token = account.idToken
            val credential = GoogleAuthProvider.getCredential(token, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (!token.isNullOrEmpty()) {
                            loginToServer("GOOGLE", token, navController)
                        }
                    }
                }
        } catch (e: Exception) {
            Log.e("LoginViewModel", "Google 로그인 실패: ${e.message}", e)
        }
    }

    // 서버로 로그인 요청
    fun loginToServer(type: String, socialAccessToken: String, navController: NavController) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(type = type, accessToken = socialAccessToken)
                val response: Response<BaseResponse<FirebaseTokenData>> = loginApi.ssoLogin(request)
                val firebaseCustomToken = response.body()?.data?.firebaseCustomToken

                // 회원가입을 위해 임시로 값 저장 (DataStore)
                userPreferences.saveTmpSocialAccessToken(socialAccessToken)
                userPreferences.saveTmpSocialType(type)

                if (response.isSuccessful) {
                    val authorizationHeader = response.headers()["authorization"]
                    val setCookieHeader = response.headers()["set-cookie"]

                    if (authorizationHeader != null && setCookieHeader != null && firebaseCustomToken != null) {
                        saveTokens(authorizationHeader, setCookieHeader, firebaseCustomToken)
                        val fcmToken = userPreferences.getFcmToken()
                        val userId = userPreferences.getUserId()
                        Log.d("FCM Token in viewmodel", fcmToken ?: "")
                        if (userId != null && fcmToken != null)
                            loginApi.setFcmToken(FcmTokenBody(userId = userId, firebaseToken = fcmToken))
                        navController.navigate("main") {
                            popUpTo("login") { inclusive = true }
                        }
                        Toast.makeText(context, "로그인 성공", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.d("LoginViewModel", "로그인 성공했지만 토큰 없음") // 디버그
                    }
                } else {
                    if(response.code() == 404){
                        Log.d("LoginViewModel", "404 응답 확인 tbti 테스트 시작")
                        navController.navigate("tbtiIntro")
                    }
                    else{
                        Log.d("LoginViewModel", "로그인 실패")
                    }
                }
            } catch (e: Exception) {
                Log.d("LoginViewModel", "로그인 에러: ${e.message}") // 디버그
            }
        }
    }



    private suspend fun saveTokens(accessToken: String, refreshToken: String, firebaseCustomToken: String) {
        userPreferences.saveTokensAndUserId(
            accessToken = accessToken,
            refreshToken = refreshToken,
            firebaseCustomToken = firebaseCustomToken
        )
    }
}
package com.tlog.viewmodel.beginning

import android.content.Context
import android.content.Intent
import com.tlog.viewmodel.base.BaseViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.tlog.api.LoginApi
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.FcmTokenBody
import com.tlog.data.api.FirebaseTokenData
import com.tlog.data.api.LoginRequest
import com.tlog.data.local.UserPreferences
import com.tlog.data.util.KakaoLoginManager
import com.tlog.data.util.NaverLoginManager
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import retrofit2.Response

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val loginApi: LoginApi
) : BaseViewModel() {




    // Kakao Manager 사용
    fun kakaoLogin(context: Context) {
        KakaoLoginManager(context) { token ->
            loginToServer("KAKAO", token)
        }.login()
    }

    // Naver Manager 사용
    fun naverLogin(context: Context) {
        NaverLoginManager(context) { token ->
            loginToServer("NAVER", token)
        }.login()
    }

    fun googleLogin(resultData: Intent?) {
        try {
            val account = GoogleSignIn.getSignedInAccountFromIntent(resultData).getResult(
                ApiException::class.java)
            val token = account.idToken
            val credential = GoogleAuthProvider.getCredential(token, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (!token.isNullOrEmpty()) {
                            loginToServer("GOOGLE", token)
                        }
                    }
                }
        } catch (e: Exception) {
            showToast(e.message ?: "Google 로그인 실패")
//            Log.e("LoginViewModel", "Google 로그인 실패 : ${e.message}", e)
        }
    }

    // 서버로 로그인 요청
    fun loginToServer(type: String, socialAccessToken: String) {
        launchSafeCall(
            action = {
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
                        if (userId != null && fcmToken != null)
                            loginApi.setFcmToken(
                                FcmTokenBody(
                                    userId = userId,
                                    firebaseToken = fcmToken
                                )
                            )
                        showToast("로그인 성공!!")
                        navigate(Screen.Main, true)
                    } else {
                        showToast("로그인 실패 (토큰 x)")
                    }
                } else {
                    if (response.code() == 404) {
                        showToast("신규회원 TBTI 테스트 진행")
                        navigate(Screen.TbtiIntro)
                    } else {
                        showToast("로그인 실패")
                    }
                }
            }
        )
    }



    private suspend fun saveTokens(accessToken: String, refreshToken: String, firebaseCustomToken: String) {
        userPreferences.saveTokensAndUserId(
            accessToken = accessToken,
            refreshToken = refreshToken,
            firebaseCustomToken = firebaseCustomToken
        )
    }
}
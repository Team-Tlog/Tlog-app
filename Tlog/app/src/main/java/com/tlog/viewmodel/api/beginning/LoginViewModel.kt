package com.tlog.viewmodel.api.beginning

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.LoginApi
import com.tlog.api.RetrofitInstance
import kotlinx.coroutines.launch
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.tlog.data.api.LoginRequest
import retrofit2.Response

class LoginViewModel(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val loginApi = RetrofitInstance.getInstance().create(LoginApi::class.java)

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }

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

    // 서버로 로그인 요청
    fun loginToServer(type: String, socialAccessToken: String) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(type = type, accessToken = socialAccessToken)
                val response: Response<Unit> = loginApi.ssoLogin(request)

                Log.d("LoginViewModel", "서버 응답 전체 헤더: ${response.headers().toMultimap()}")

                if (response.isSuccessful) {
                    val authorizationHeader = response.headers()["authorization"]
                    val setCookieHeader = response.headers()["set-cookie"]

                    Log.d("LoginViewModel", "Authorization 헤더: $authorizationHeader")
                    Log.d("LoginViewModel", "Set-Cookie 헤더: $setCookieHeader")

                    if (authorizationHeader != null && setCookieHeader != null) {
                        saveTokens(authorizationHeader, setCookieHeader)
                        Log.d("LoginViewModel", "로그인 성공! 토큰 저장 완료")
                    } else {
                        Log.d("LoginViewModel", "로그인 성공했지만 토큰 없음")
                    }
                } else {
                    Log.d("LoginViewModel", "로그인 실패: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.d("LoginViewModel", "로그인 에러: ${e.message}")
            }
        }
    }

    private suspend fun saveTokens(accessToken: String, refreshToken: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = accessToken
            preferences[REFRESH_TOKEN_KEY] = refreshToken
        }
    }
}

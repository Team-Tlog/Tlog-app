package com.tlog.viewmodel.api.beginning

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
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

    /** 카카오 로그인 */
    fun kakaoLogin(context: Context) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            // 카카오톡으로 로그인 시도
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.d("LoginViewModel", "카카오톡 로그인 실패: ${error.localizedMessage}")

                    // 사용자가 카카오톡 설치했지만, 로그인 취소한 경우
                    if (error is com.kakao.sdk.common.model.ClientError && error.reason == com.kakao.sdk.common.model.ClientErrorCause.Cancelled) {
                        Log.d("LoginViewModel", "사용자가 로그인 취소")
                        return@loginWithKakaoTalk
                    }

                    // 다른 이유로 카카오톡 로그인 실패 → 카카오계정(웹뷰) 로그인 시도
                    loginWithKakaoAccount(context)
                } else if (token != null) {
                    Log.d("LoginViewModel", "카카오톡 로그인 성공")
                    loginToServer("KAKAO", token.accessToken)
                }
            }
        } else {
            // 카카오톡 설치 안 되어있으면 → 카카오계정(웹뷰) 로그인 바로 시도
            loginWithKakaoAccount(context)
        }
    }

    private fun loginWithKakaoAccount(context: Context) {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            if (error != null) {
                Log.d("LoginViewModel", "카카오계정 로그인 실패: ${error.localizedMessage}")

                if (error is com.kakao.sdk.common.model.ClientError && error.reason == com.kakao.sdk.common.model.ClientErrorCause.Cancelled) {
                    Log.d("LoginViewModel", "사용자가 계정 로그인 취소")
                    return@loginWithKakaoAccount
                }
            } else if (token != null) {
                Log.d("LoginViewModel", "카카오계정 로그인 성공")
                Log.d("LoginViewModel", "카카오 accessToken: ${token.accessToken}")
                loginToServer("KAKAO", token.accessToken)
            }
        }
    }


    /** 서버 로그인 */
    /** 서버 로그인 */
    private fun loginToServer(type: String, socialAccessToken: String) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(type = type, accessToken = socialAccessToken)
                val response: Response<Unit> = loginApi.ssoLogin(request)

                // 디버깅용 전체 헤더 출력
                Log.d("LoginViewModel", "서버 응답 전체 헤더: ${response.headers().toMultimap()}")

                if (response.isSuccessful) {
                    val authorizationHeader = response.headers()["authorization"]
                    val setCookieHeader = response.headers()["set-cookie"]

                    // 디버깅용 개별 헤더 출력
                    Log.d("LoginViewModel", "Authorization 헤더: $authorizationHeader")
                    Log.d("LoginViewModel", "Set-Cookie 헤더: $setCookieHeader")

                    val accessToken = authorizationHeader
                    val refreshToken = setCookieHeader

                    if (accessToken != null && refreshToken != null) {
                        saveTokens(accessToken, refreshToken)
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

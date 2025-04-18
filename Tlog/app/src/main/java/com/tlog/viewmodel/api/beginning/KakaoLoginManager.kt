package com.tlog.viewmodel.api.beginning

import android.content.Context
import android.util.Log
import com.kakao.sdk.user.UserApiClient

class KakaoLoginManager(
    private val context: Context,
    private val onTokenReceived: (accessToken: String) -> Unit
) {
    fun login() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                handleResult(token?.accessToken, error)
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                handleResult(token?.accessToken, error)
            }
        }
    }

    private fun handleResult(token: String?, error: Throwable?) {
        if (error != null) {
            Log.d("KakaoLoginManager", "카카오 로그인 실패: ${error.localizedMessage}")
            Log.e("KakaoLoginManager", "로그인 실패", error)
        } else if (token != null) {
            Log.d("KakaoLoginManager", "카카오 로그인 성공 - token: $token")
            onTokenReceived(token)
        }
    }
}

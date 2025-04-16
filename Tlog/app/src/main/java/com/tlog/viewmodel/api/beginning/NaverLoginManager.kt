package com.tlog.viewmodel.api.beginning

import android.content.Context
import android.util.Log
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback

class NaverLoginManager(
    private val context: Context,
    private val onTokenReceived: (accessToken: String) -> Unit
) {
    fun login() {
        NaverIdLoginSDK.authenticate(context, object : OAuthLoginCallback {
            override fun onSuccess() {
                val token = NaverIdLoginSDK.getAccessToken()
                if (token != null) {
                    Log.d("NaverLoginManager", "네이버 로그인 성공 - token: $token")
                    onTokenReceived(token)
                } else {
                    Log.d("NaverLoginManager", "토큰이 null")
                }
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Log.d("NaverLoginManager", "네이버 로그인 실패 - $httpStatus: $message")
            }

            override fun onError(errorCode: Int, message: String) {
                Log.d("NaverLoginManager", "네이버 로그인 에러 - $errorCode: $message")
            }
        })
    }
}

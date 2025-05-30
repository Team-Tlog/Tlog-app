package com.tlog

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.local.UserPreferences
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {
    @Inject lateinit var userPreferences: UserPreferences
    @Inject lateinit var tokenProvider: TokenProvider

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this,BuildConfig.KAKAO_NATIVE_APP_KEY)

        NaverIdLoginSDK.initialize(
            context = this,
            clientId = BuildConfig.NAVER_CLIENT_ID,
            clientSecret = BuildConfig.NAVER_CLIENT_SECRET,
            clientName = "Tlog"
        )

        // 앱 시작 시 tokenProvider에 토큰 저장
        CoroutineScope(Dispatchers.IO).launch {
            val userId = userPreferences.getUserId()
            val accessToken = userPreferences.getAccessToken()
            val refreshToken = userPreferences.getRefreshToken()
            val firebaseCustomToken = userPreferences.getFirebaseCustomToken()

            tokenProvider.setUserId(userId)
            tokenProvider.setAccessToken(accessToken)
            tokenProvider.setRefreshToken(refreshToken)
            tokenProvider.setFirebaseCustomToken(firebaseCustomToken)
        }
    }
}

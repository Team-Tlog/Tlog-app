package com.tlog

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)

        NaverIdLoginSDK.initialize(
            context = this,
            clientId = BuildConfig.NAVER_CLIENT_ID,
            clientSecret = BuildConfig.NAVER_CLIENT_SECRET,
            clientName = "Tlog"
        )
    }
}

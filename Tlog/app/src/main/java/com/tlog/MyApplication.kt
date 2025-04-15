package com.tlog

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "1672339d7fda09e058a54ca329612700") //카카오 네이티브 앱키

        NaverIdLoginSDK.initialize(
            context = this,
            clientId = "B1TWiZjz425aIf9yeepw",
            clientSecret = "t6lGxiGIC_",
            clientName = "Tlog"                          // 앱 이름
        )
    }
}

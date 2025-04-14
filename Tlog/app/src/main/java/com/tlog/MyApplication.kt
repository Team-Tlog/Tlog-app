package com.tlog

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "1672339d7fda09e058a54ca329612700") // ← 실제 카카오 네이티브 앱 키 넣어야 해
    }
}

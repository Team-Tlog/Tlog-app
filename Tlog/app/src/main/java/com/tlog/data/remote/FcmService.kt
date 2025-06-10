package com.tlog.data.remote

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.tlog.data.local.UserPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class FcmService: FirebaseMessagingService() {
    @Inject lateinit var userPreferences: UserPreferences

    // 앱 설치시 자동으로 발급 -> dataStore에 저장
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM Token", token)
        CoroutineScope(Dispatchers.IO).launch {
            userPreferences.setFcmToken(token)
        }
    }
}
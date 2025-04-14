package com.tlog.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

object UserPreferences {
    private val Context.dataStore by preferencesDataStore(name = "user")

    // userId 라는 key 값으로 value 저장 (map 형식)
    private val USER_ID = stringPreferencesKey("userId")

    // 유저아이디 저장
    suspend fun saveUserId(context: Context, userId: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID] = userId
        }
    }

    // 유저아이디 호출
    suspend fun getUserId(context: Context): String? {
        val prefs = context.dataStore.data.first()
        return prefs[USER_ID]
    }
}
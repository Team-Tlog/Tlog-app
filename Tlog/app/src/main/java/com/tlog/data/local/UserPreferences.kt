package com.tlog.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import android.util.Base64
import android.util.Log
import org.json.JSONObject


//private val Context.userDataStore by preferencesDataStore(name = "user")


object UserPreferences {
    private val Context.dataStore by preferencesDataStore(name = "user")


    private val USER_ID = stringPreferencesKey("userId")
    private val ACCESS_TOKEN = stringPreferencesKey("accessToken")
    private val REFRESH_TOKEN = stringPreferencesKey("refreshToken")

    suspend fun saveTokensAndUserId(context: Context, accessToken: String, refreshToken: String) {
        val userId = userIdFromJwt(accessToken)

        Log.d("tokens", userId?:"없다고")
        Log.d("tokens", accessToken)
        Log.d("tokens", refreshToken)

        if (userId == null) {
            return
        }
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = userId
            preferences[ACCESS_TOKEN] = accessToken
            preferences[REFRESH_TOKEN] = refreshToken
        }
    }

    suspend fun getUserId(context: Context): String? {
        val prefs = context.dataStore.data.first()
        return prefs[USER_ID]
    }

    suspend fun getAccessToken(context: Context): String? {
        val prefs = context.dataStore.data.first()
        return prefs[ACCESS_TOKEN]
    }

    suspend fun getRefreshToken(context: Context): String? {
        val prefs = context.dataStore.data.first()
        return prefs[REFRESH_TOKEN]
    }



    // JWT 토큰에서 UserId 파싱하는 함수
    fun userIdFromJwt(jwtToken: String): String? {
        return try {
            val parts = jwtToken.split(".")
            if (parts.size < 2) return null

            val payloadBytes = Base64.decode(parts[1], Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
            val payloadJson = JSONObject(String(payloadBytes))
            payloadJson.getString("sub")
        } catch (e: Exception) {
            null
        }
    }
}
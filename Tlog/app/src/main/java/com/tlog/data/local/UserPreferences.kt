package com.tlog.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import android.util.Base64
import android.util.Log
import com.tlog.api.retrofit.TokenProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

// DataStore를 재사용 하기 위해 전역으로 선언 + 싱글톤 보장 (중복 생성 방지)
private val Context.dataStore by preferencesDataStore(name = "user")


@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context,
    private val tokenProvider: TokenProvider
) {

    private val USER_ID = stringPreferencesKey("userId")
    private val ACCESS_TOKEN = stringPreferencesKey("accessToken")
    private val REFRESH_TOKEN = stringPreferencesKey("refreshToken")
    private val FIREBASE_CUSTOM_TOKEN = stringPreferencesKey("firebaseCustomToken")

    suspend fun saveTokensAndUserId(accessToken: String, refreshToken: String, firebaseCustomToken: String? = null) {
        val userId = userIdFromJwt(accessToken)

        if (userId == null) {
            return
        }

        context.dataStore.edit { preferences ->
            preferences[USER_ID] = userId
            preferences[ACCESS_TOKEN] = accessToken
            preferences[REFRESH_TOKEN] = refreshToken
            if (firebaseCustomToken != null)
                preferences[FIREBASE_CUSTOM_TOKEN] = firebaseCustomToken
        }

        tokenProvider.setUserId(userId)
        tokenProvider.setAccessToken(accessToken)
        tokenProvider.setRefreshToken(refreshToken)
        if (firebaseCustomToken != null)
            tokenProvider.setFirebaseCustomToken(firebaseCustomToken)
    }

    suspend fun getUserId(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[USER_ID]
    }

    suspend fun getAccessToken(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[ACCESS_TOKEN]
    }

    suspend fun getRefreshToken(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[REFRESH_TOKEN]
    }

    suspend fun getFirebaseCustomToken(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[FIREBASE_CUSTOM_TOKEN]
    }

    // api 실패 시 사용? -> 위에 함수로 해도 될 것 같은데 일단 대기
    suspend fun updateTokens(accessToken: String, refreshToken: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken
            preferences[REFRESH_TOKEN] = refreshToken
        }
        tokenProvider.setAccessToken(accessToken)
        tokenProvider.setRefreshToken(refreshToken)
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
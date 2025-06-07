package com.tlog.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import android.util.Base64
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
    private val SNS_USER_ID = stringPreferencesKey("snsUserId")
    private val SNS_ID = stringPreferencesKey("snsId")

    suspend fun saveTokensAndUserId(accessToken: String, refreshToken: String, firebaseCustomToken: String? = null) {
        val (userId, snsId) = userIdFromJwt(accessToken)

        if (userId == null) {
            return
        }

        context.dataStore.edit { preferences ->
            preferences[USER_ID] = userId
            preferences[ACCESS_TOKEN] = accessToken
            preferences[REFRESH_TOKEN] = refreshToken
            if (firebaseCustomToken != null)
                preferences[FIREBASE_CUSTOM_TOKEN] = firebaseCustomToken
            if (snsId != null)
                preferences[SNS_ID] = snsId
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

    suspend fun getSnsId(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[SNS_ID]
    }

    fun userIdFromJwt(jwtToken: String): Pair<String?, String?> {
        return try {
            val parts = jwtToken.split(".")
            if (parts.size < 2) return Pair(null, null)

            val payloadBytes = Base64.decode(parts[1], Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
            val payloadJson = JSONObject(String(payloadBytes))
            val userId = payloadJson.getString("sub")
            val snsId = if (payloadJson.has("snsId")) payloadJson.getString("snsId") else null
            Pair(userId, snsId)
        } catch (e: Exception) {
            Pair(null, null)
        }
    }

    suspend fun clearTokens() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }

        tokenProvider.setSnsId(null)
        tokenProvider.setUserId(null)
        tokenProvider.setAccessToken(null)
        tokenProvider.setRefreshToken(null)
        tokenProvider.setFirebaseCustomToken(null)
    }
}
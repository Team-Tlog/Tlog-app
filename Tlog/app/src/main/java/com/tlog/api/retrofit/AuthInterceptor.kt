package com.tlog.api.retrofit

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

class AuthInterceptor @Inject constructor(
    private val tokenProvider: TokenProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = tokenProvider.getAccessToken()

        val newRequest = chain.request().newBuilder()
            .apply {
                accessToken?.let {
                    addHeader("Authorization", "Bearer $it")
                }
            }
            .build()

        return chain.proceed(newRequest)
    }
}

@Singleton
class TokenProvider @Inject constructor() {
    private var userId: String? = null
    private var accessToken: String? = null
    private var refreshToken: String? = null
    private var firebaseCustomToken: String? = null

    fun getUserId(): String? = userId
    fun getAccessToken(): String? = accessToken
    fun getRefreshToken(): String? = refreshToken
    fun getFirebaseCustomToken(): String? = firebaseCustomToken


    fun setUserId(id: String?) {
        userId = id
    }

    fun setAccessToken(token: String?) {
        accessToken = token
    }

    fun setRefreshToken(token: String?) {
        refreshToken = token
    }

    fun setFirebaseCustomToken(token: String?) {
        firebaseCustomToken = token
    }
}
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
    private var accessToken: String? = null

    fun getAccessToken(): String? = accessToken

    fun setAccessToken(token: String?) {
        accessToken = token
    }
}
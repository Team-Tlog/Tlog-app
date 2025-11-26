package com.tlog.data.remote

import com.tlog.data.local.TokenProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

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

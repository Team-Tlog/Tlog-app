package com.tlog.api

import com.tlog.data.api.BaseResponse
import com.tlog.data.api.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("api/auth/login/user")
    suspend fun ssoLogin(
        @Body loginRequest: LoginRequest
    ): Response<BaseResponse<FirebaseTokenData>>
}

data class FirebaseTokenData(
    val firebaseCustomToken: String
)
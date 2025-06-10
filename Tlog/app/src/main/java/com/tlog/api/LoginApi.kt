package com.tlog.api

import com.tlog.data.api.BaseResponse
import com.tlog.data.api.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginApi {
    @POST("api/auth/login/user")
    suspend fun ssoLogin(
        @Body loginRequest: LoginRequest
    ): Response<BaseResponse<FirebaseTokenData>>

    @POST("api/auth/logout")
    suspend fun ssoLogout(
        @Header("Cookie") cookie: String
    ): BaseResponse<Unit>

    @POST("/api/notify")
    suspend fun setFcmToken(
        @Body fcmTokenBody: FcmTokenBody
    ): BaseResponse<Unit>
}

data class FirebaseTokenData(
    val firebaseCustomToken: String
)

data class FcmTokenBody(
    val userId: String,
    val firebaseToken: String
)
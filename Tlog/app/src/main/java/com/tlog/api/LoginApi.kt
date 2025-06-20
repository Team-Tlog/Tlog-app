package com.tlog.api

import com.tlog.data.api.BaseResponse
import com.tlog.data.api.FcmTokenBody
import com.tlog.data.api.FirebaseTokenData
import com.tlog.data.api.LoginRequest
import com.tlog.data.api.RegisterRequest
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

    @POST("/api/auth/register/user")
    suspend fun ssoRegister(
        @Body request: RegisterRequest
    ): Response<BaseResponse<FirebaseTokenData>>
}
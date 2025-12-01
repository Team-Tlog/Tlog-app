package com.tlog.api

import com.tlog.data.model.response.base.BaseResponse
import com.tlog.data.model.request.auth.FcmTokenRequest
import com.tlog.data.model.response.auth.FirebaseTokenResponse
import com.tlog.data.model.request.auth.LoginRequest
import com.tlog.data.model.request.auth.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginApi {
    @POST("api/auth/login/user")
    suspend fun ssoLogin(
        @Body loginRequest: LoginRequest
    ): Response<BaseResponse<FirebaseTokenResponse>>

    @POST("api/auth/logout")
    suspend fun ssoLogout(
        @Header("Cookie") cookie: String
    ): BaseResponse<Unit>

    @POST("/api/notify")
    suspend fun setFcmToken(
        @Body fcmTokenBody: FcmTokenRequest
    ): BaseResponse<Unit>

    @POST("/api/auth/register/user")
    suspend fun ssoRegister(
        @Body request: RegisterRequest
    ): Response<BaseResponse<FirebaseTokenResponse>>
}
package com.tlog.data.repository

import com.tlog.api.LoginApi
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.FcmTokenBody
import com.tlog.data.api.FirebaseTokenData
import com.tlog.data.api.RegisterRequest
import jakarta.inject.Inject
import retrofit2.Response

class ChooseMyTypeRepository @Inject constructor(
    private val loginRetrofitInstance: LoginApi
) {
    suspend fun ssoRegister(request: RegisterRequest): Response<BaseResponse<FirebaseTokenData>> {
        return loginRetrofitInstance.ssoRegister(request)
    }

    suspend fun setFcmToken(request: FcmTokenBody): BaseResponse<Unit> {
        return loginRetrofitInstance.setFcmToken(request)
    }
}
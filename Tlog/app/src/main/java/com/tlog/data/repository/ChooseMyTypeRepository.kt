package com.tlog.data.repository

import com.tlog.api.LoginApi
import com.tlog.data.model.response.base.BaseResponse
import com.tlog.data.model.request.auth.FcmTokenRequest
import com.tlog.data.model.response.auth.FirebaseTokenResponse
import com.tlog.data.model.request.auth.RegisterRequest
import jakarta.inject.Inject
import retrofit2.Response

class ChooseMyTypeRepository @Inject constructor(
    private val loginRetrofitInstance: LoginApi
) {
    suspend fun ssoRegister(request: RegisterRequest): Response<BaseResponse<FirebaseTokenResponse>> {
        return loginRetrofitInstance.ssoRegister(request)
    }

    suspend fun setFcmToken(request: FcmTokenRequest): BaseResponse<Unit> {
        return loginRetrofitInstance.setFcmToken(request)
    }
}

package com.tlog.data.repository

import com.tlog.api.LoginApi
import com.tlog.data.api.BaseResponse
import javax.inject.Inject

class MyPageRepository @Inject constructor(
    private val retrofitInstance: LoginApi
) {
    suspend fun logout(refreshToken: String): BaseResponse<Unit> {
        val cookieHeader = "refreshToken=$refreshToken"
        return retrofitInstance.ssoLogout(cookieHeader)
    }
}
package com.tlog.data.repository

import com.tlog.api.LoginApi
import com.tlog.api.UserApi
import com.tlog.api.UserInfo
import com.tlog.data.api.BaseResponse
import javax.inject.Inject

class MyPageRepository @Inject constructor(
    private val loginRetrofitInstance: LoginApi,
    private val userRetrofitInstance: UserApi
) {
    suspend fun logout(refreshToken: String): BaseResponse<Unit> {
        val cookieHeader = "refreshToken=$refreshToken"
        return loginRetrofitInstance.ssoLogout(cookieHeader)
    }

    suspend fun getUserInfo(): BaseResponse<UserInfo> {
        return userRetrofitInstance.getUserInfo()
    }
}
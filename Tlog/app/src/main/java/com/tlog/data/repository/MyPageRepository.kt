package com.tlog.data.repository

import android.util.Log
import com.tlog.api.LoginApi
import com.tlog.api.UserApi
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.ProfileImageRequest
import com.tlog.data.model.user.User
import javax.inject.Inject

class MyPageRepository @Inject constructor(
    private val loginRetrofitInstance: LoginApi,
    private val userRetrofitInstance: UserApi
) {
    suspend fun logout(refreshToken: String): BaseResponse<Unit> {
        val cookieHeader = "refreshToken=$refreshToken"
        return loginRetrofitInstance.ssoLogout(cookieHeader)
    }

    suspend fun getUserInfo(): BaseResponse<User> {
        return userRetrofitInstance.getUserInfo()
    }

    suspend fun updateProfileImage(image : ProfileImageRequest): BaseResponse<String>{
        val result = userRetrofitInstance.updateProfileImage(image)
        Log.d("UpdateProfileImage", result.toString())
        return result
    }
}
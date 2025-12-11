package com.tlog.data.repository

import android.util.Log
import com.tlog.api.LoginApi
import com.tlog.api.UserApi
import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.data.dto.request.auth.FeedbackRequest
import com.tlog.data.dto.request.auth.ProfileImageRequest
import com.tlog.data.dto.user.UserDto
import com.tlog.domain.mapper.toDomain
import com.tlog.domain.model.share.User
import javax.inject.Inject

class MyPageRepository @Inject constructor(
    private val loginRetrofitInstance: LoginApi,
    private val userRetrofitInstance: UserApi
) {
    suspend fun logout(refreshToken: String) {
        val cookieHeader = "refreshToken=$refreshToken"
        loginRetrofitInstance.ssoLogout(cookieHeader)
    }

    suspend fun getUserInfo(): User {
        return userRetrofitInstance.getUserInfo().data.toDomain()
    }

    suspend fun updateProfileImage(image : ProfileImageRequest) {
        userRetrofitInstance.updateProfileImage(image)
    }

    suspend fun submitFeedback(request: FeedbackRequest) {
        userRetrofitInstance.submitFeedback(request)
    }
}

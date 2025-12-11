package com.tlog.api

import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.data.dto.request.auth.FeedbackRequest
import com.tlog.data.dto.request.auth.ProfileImageRequest
import com.tlog.data.dto.user.UserDto
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    // 기본 마이페이지 조회 (SNS 아님)
    @GET("api/users/my-page")
    suspend fun getUserInfo(): BaseResponse<UserDto>

    //마이페이지에서 프로필사진 업로드
    @POST("/api/users/profile-image")
    suspend fun updateProfileImage(
        @Body request: ProfileImageRequest
    ): BaseResponse<String>

    // 피드백 제출
    @POST("api/operation/feedback")
    suspend fun submitFeedback(
        @Body request: FeedbackRequest
    ): BaseResponse<Unit>
}

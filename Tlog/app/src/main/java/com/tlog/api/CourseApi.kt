package com.tlog.api

import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.data.dto.response.course.UserCourseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CourseApi {
    @GET("/api/course/user/{userId}")
    suspend fun getUserCourseList(
        @Path("userId") userId: String
    ): BaseResponse<List<UserCourseResponse>>
}

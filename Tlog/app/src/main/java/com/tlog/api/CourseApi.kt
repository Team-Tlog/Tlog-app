package com.tlog.api

import com.tlog.data.api.BaseResponse
import com.tlog.data.model.travel.UserCourseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CourseApi {

    @GET("/api/course/user/{userId}")
    suspend fun getUserCourses(
        @Path("userId") userId: String
    ): BaseResponse<List<UserCourseResponse>>


}
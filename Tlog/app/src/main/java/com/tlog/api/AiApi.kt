package com.tlog.api

import com.tlog.data.model.response.base.BaseResponse
import com.tlog.data.model.response.course.CourseItem
import com.tlog.data.model.travel.AiRequest
import com.tlog.data.model.travel.AiTravel
import com.tlog.data.model.travel.CourseResponse
import com.tlog.data.model.travel.CourseSaveRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AiApi {
    @POST("/api/course/recommendations")
    suspend fun getAiCourseRecommendations(
        @Query("ownerId") ownerId: String,
        @Query("ownerType") ownerType: String,
        @Body aiRequest: AiRequest
    ): BaseResponse<Map<String, List<AiTravel>>>

    @POST("/api/course")
    suspend fun saveCourse(
        @Query("ownerId") ownerId: String,
        @Query("ownerType") ownerType: String,
        @Body courseSaveRequest: CourseSaveRequest
    ): BaseResponse<String>

    @GET("/api/course/closest")
    suspend fun getCourse(
        @Query("ownerId") ownerId: String,
        @Query("ownerType") ownerType: String = "USER"
    ): BaseResponse<CourseResponse>

    @GET("/api/course/user/{userId}")
    suspend fun getUserCourses(
        @Path("userId") userId: String
    ): BaseResponse<List<CourseItem>>
}








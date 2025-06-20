package com.tlog.data.repository

import com.tlog.api.CourseApi
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.UserCourseResponse
import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val courseApi: CourseApi
) {
    suspend fun getUserCourses(userId: String): BaseResponse<List<UserCourseResponse>> {
        return courseApi.getUserCourseList(userId)
    }
}
package com.tlog.data.repository

import com.tlog.api.CourseApi
import com.tlog.data.model.response.base.BaseResponse
import com.tlog.data.model.response.course.UserCourseResponse
import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val courseApi: CourseApi
) {
    suspend fun getUserCourses(userId: String): BaseResponse<List<UserCourseResponse>> {
        return courseApi.getUserCourseList(userId)
    }
}

package com.tlog.data.repository

import com.tlog.api.AiApi
import com.tlog.data.api.BaseResponse
import com.tlog.data.model.travel.CourseResponse
import javax.inject.Inject


class MyTravelingCourseRepository @Inject constructor(
    private val aiApi: AiApi
) {
    suspend fun getCourse(courseId: String): BaseResponse<CourseResponse> {
        return aiApi.getCourse(courseId)
    }
}

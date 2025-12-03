package com.tlog.data.repository

import com.tlog.api.AiApi
import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.data.dto.response.travel.CourseResponse
import javax.inject.Inject


class MyTravelingCourseRepository @Inject constructor(
    private val aiApi: AiApi
) {
    suspend fun getCourse(ownerId: String): BaseResponse<CourseResponse> {
        return aiApi.getCourse(ownerId)
    }
}

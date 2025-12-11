package com.tlog.data.repository

import com.tlog.api.AiApi
import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.data.dto.response.travel.CourseResponse
import com.tlog.domain.mapper.toDomain
import com.tlog.domain.model.course.DailyCourse
import javax.inject.Inject


class MyTravelingCourseRepository @Inject constructor(
    private val aiApi: AiApi
) {
    suspend fun getCourse(ownerId: String): List<DailyCourse> {
        return aiApi.getCourse(ownerId).data.toDomain()
    }
}

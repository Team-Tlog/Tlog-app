package com.tlog.data.repository

import com.tlog.api.AiApi
import com.tlog.data.model.response.base.BaseResponse
import com.tlog.data.model.request.travel.CourseSaveRequest
import javax.inject.Inject

class AiRecommendCourseResultRepository @Inject constructor(
    private val aiApi: AiApi
) {
    suspend fun saveCourse(
        ownerId: String,
        ownerType: String,
        courseSaveRequest: CourseSaveRequest,
    ): BaseResponse<String> {
        return aiApi.saveCourse(ownerId, ownerType, courseSaveRequest)
    }
}

package com.tlog.data.repository

import com.tlog.api.AiApi
import com.tlog.api.UserApi
import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.data.dto.request.travel.AiRequest
import com.tlog.data.dto.response.travel.AiTravelDto
import com.tlog.data.dto.travel.CartDto
import com.tlog.domain.mapper.toDomain
import com.tlog.domain.model.course.AiCourse
import jakarta.inject.Inject

class AiCourseSelectCartRepository @Inject constructor(
    private val userApi: UserApi,
    private val aiApi: AiApi
) {
    suspend fun getUserCart(userId: String): List<CartDto> {
        return userApi.getUserCart(userId).data
    }

    suspend fun getAiCourseRecommendations(
        ownerId: String,
        ownerType: String,
        aiRequest: AiRequest,
    ): List<AiCourse> {
        return aiApi
            .getAiCourseRecommendations(ownerId, ownerType, aiRequest)
            .data
            .toDomain()
    }
}

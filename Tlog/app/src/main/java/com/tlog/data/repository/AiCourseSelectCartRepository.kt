package com.tlog.data.repository

import com.tlog.api.AiApi
import com.tlog.api.ScrapAndCartApi
import com.tlog.data.dto.request.travel.AiRequest
import com.tlog.data.dto.travel.CartDto
import com.tlog.domain.mapper.toDomain
import com.tlog.domain.model.course.AiCourse
import jakarta.inject.Inject

class AiCourseSelectCartRepository @Inject constructor(
    private val scrapAndCartApi: ScrapAndCartApi,
    private val aiApi: AiApi
) {
    suspend fun getUserCart(userId: String): List<CartDto> {
        return scrapAndCartApi.getUserCart(userId).data
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

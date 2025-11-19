package com.tlog.data.repository

import com.tlog.api.AiApi
import com.tlog.api.AiRequest
import com.tlog.api.UserApi
import com.tlog.data.model.travel.Cart
import jakarta.inject.Inject

class AiCourseSelectCartRepository @Inject constructor (
    private val userApi: UserApi,
    private val aiApi: AiApi
) {
    suspend fun getUserCart(userId: String): List<Cart> {
        return userApi.getUserCart(userId).data
    }

    suspend fun getAiCourseRecommendations(ownerId: String, ownerType: String, aiRequest: AiRequest) {
        aiApi.getAiCourseRecommendations(ownerId, ownerType, aiRequest)
    }
}
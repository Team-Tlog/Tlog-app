package com.tlog.api

import com.tlog.data.api.BaseResponse
import com.tlog.data.model.travel.Cart
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AiApi {
    @POST("/api/course/recommendations")
    suspend fun getAiCourseRecommendations(
        @Query("ownerId") ownerId: String,
        @Query("ownerType") ownerType: String,
        @Body aiRequest: AiRequest
    ): BaseResponse<Unit>
}

data class AiRequest(
    val city: String,
    val region_codes: List<Int>,
    val dailyPlans: List<DailyPlan>,
    val wishlist: List<Cart>
)

data class DailyPlan(
    val date: String,
    val placeCount: Int
)

package com.tlog.api

import com.tlog.data.api.BaseResponse
import com.tlog.data.api.ReviewRequest
import com.tlog.data.model.travel.Travel
import retrofit2.http.Body
import retrofit2.http.POST

interface TravelApi {
    @POST("/api/destinations")
    suspend fun addTravel(
        @Body travel: Travel
    ): BaseResponse<String?>

    @POST("/api/reviews")
    suspend fun addReview(
        @Body reviewRequest: ReviewRequest
    ): BaseResponse<String?>

}


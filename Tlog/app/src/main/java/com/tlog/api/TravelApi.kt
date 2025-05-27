package com.tlog.api

import com.tlog.data.api.BaseResponse
import com.tlog.data.api.ReviewRequest
import com.tlog.data.model.travel.AddTravelRequest
import com.tlog.data.model.travel.TravelDestinationResponse
import com.tlog.data.model.travel.TravelRecommendPagedResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TravelApi {
    @POST("/api/destinations")
    suspend fun addTravel(
        @Body travel: AddTravelRequest
    ): BaseResponse<String?>

    @POST("/api/reviews")
    suspend fun addReview(
        @Body reviewRequest: ReviewRequest
    ): BaseResponse<String?>

    @GET("/api/destinations")
        suspend fun getDestinations(
            @Query("page") page: Int,
            @Query("size") size: Int,
            @Query("sort") sort: String
        ): TravelRecommendPagedResponse<TravelDestinationResponse>
}

package com.tlog.api

import com.tlog.data.model.travel.Travel
import com.tlog.data.model.travel.TravelDetailResponse
import com.tlog.data.model.travel.TravelDestinationResponse
import com.tlog.data.model.travel.TravelRecommendPagedResponse
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.ReviewRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TravelApi {

    @GET("/api/destinations")
    suspend fun getDestinations(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String
    ): TravelRecommendPagedResponse<TravelDestinationResponse>

    @GET("/api/destinations/{id}")
    suspend fun getDestinationById(
        @Path("id") id: String
    ): TravelDetailResponse

    @POST("/api/destinations")
    suspend fun addTravel(
        @Body travel: Travel
    ): BaseResponse<String?>

    @POST("/api/reviews")
    suspend fun addReview(
        @Body reviewRequest: ReviewRequest
    ): BaseResponse<String?>
}
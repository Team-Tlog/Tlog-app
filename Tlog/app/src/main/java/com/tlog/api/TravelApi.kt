package com.tlog.api

import com.tlog.data.dto.request.travel.AddTravelRequest
import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.data.dto.response.travel.TravelDetailResponse
import com.tlog.data.dto.response.travel.TravelRecommendPagedResponse
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
        @Query("sort") sort: List<String>,
        @Query("city") city: String,
        @Query("sortType") sortType: String? = null
    ): BaseResponse<TravelRecommendPagedResponse>

    @GET("/api/destinations/{id}")
    suspend fun getDestinationById(
        @Path("id") id: String
    ): BaseResponse<TravelDetailResponse>

    @POST("/api/destinations")
    suspend fun addTravel(
        @Body travel: AddTravelRequest
    ): BaseResponse<String?>
}

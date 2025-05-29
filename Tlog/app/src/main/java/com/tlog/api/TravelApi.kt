package com.tlog.api

import com.tlog.data.api.BaseResponse
import com.tlog.data.api.ReviewRequest
import com.tlog.data.api.ScrapDestinationResponse
import com.tlog.data.model.travel.AddTravelRequest
import com.tlog.data.model.travel.TravelDetailResponse
import com.tlog.data.model.travel.TravelDestinationResponse
import com.tlog.data.model.travel.TravelRecommendPagedResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
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
        @Body travel: AddTravelRequest
    ): BaseResponse<String?>

    @POST("/api/reviews")
    suspend fun addReview(
        @Body reviewRequest: ReviewRequest
    ): BaseResponse<String?>

    @PUT("/api/scrap/user/{userId}")
    @Headers("Content-Type: text/plain")
    suspend fun scrapDestination(
        @Path("userId") userId: String,
        @Body travelId: okhttp3.RequestBody
    ): BaseResponse<Unit>

    @DELETE("/api/scrap/user/{userId}/destination/{destId}")
    suspend fun deleteScrapDestination(
        @Path("userId") userId: String,
        @Path("destId") destinationId: String
    ): BaseResponse<Unit>

    @GET("/api/scrap/user/{userId}")
    suspend fun getUserScraps(
        @Path("userId") userId: String
    ): BaseResponse<List<ScrapDestinationResponse>>
}

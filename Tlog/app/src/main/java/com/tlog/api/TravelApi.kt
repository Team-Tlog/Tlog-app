package com.tlog.api

import com.tlog.data.api.BaseListResponse
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.ReviewRequest
import com.tlog.data.api.ReviewResponse
import com.tlog.data.api.ScrapDestinationResponse
import com.tlog.data.model.travel.AddTravelRequest
import com.tlog.data.model.travel.DetailReview
import com.tlog.data.model.travel.Pageable
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
        @Query("sort") sort: List<String>,
        @Query("city") city: String,
        @Query("sortType") sortType: String? = null,
        @Query("tbti") tbti: String? = null
    ): BaseResponse<TravelRecommendPagedResponse>

    @GET("/api/destinations/{id}")
    suspend fun getDestinationById(
        @Path("id") id: String
    ): BaseResponse<TravelDetailResponse>

    @POST("/api/destinations")
    suspend fun addTravel(
        @Body travel: AddTravelRequest
    ): BaseResponse<String?>


    // review
    @POST("/api/reviews")
    suspend fun addReview(
        @Body reviewRequest: ReviewRequest
    ): BaseResponse<String?>

    @GET("/api/reviews/{destinationId}")
    suspend fun getReviewList(
        @Path("destinationId") destinationId: String,
        @Query("sortType") sortType: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: List<String>
    ): BaseListResponse<List<DetailReview>>
}

package com.tlog.api

import com.tlog.data.dto.request.review.ReviewRequest
import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.data.dto.response.review.ReviewsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewApi {
    @POST("/api/reviews")
    suspend fun addReview(
        @Body reviewRequest: ReviewRequest
    ): BaseResponse<String?>

    @GET("/api/reviews/{destinationId}")
    suspend fun getReviews(
        @Path("destinationId") destinationId: String,
        @Query("sortType") sortType: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: List<String>
    ): BaseResponse<ReviewsResponse>
}

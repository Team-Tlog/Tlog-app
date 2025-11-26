package com.tlog.data.repository

import android.util.Log
import com.tlog.api.TravelApi
import com.tlog.data.model.response.base.BaseResponse
import com.tlog.data.model.response.review.ReviewsResponse
import com.tlog.data.model.request.review.ReviewRequest
import jakarta.inject.Inject

class ReviewRepository @Inject constructor(
    private val retrofitInstance: TravelApi
) {
    suspend fun addReview(review: ReviewRequest): BaseResponse<String?>{
        val result = retrofitInstance.addReview(review)
        Log.d("ReviewRepository", "addReview: $result")
        return result
    }

    suspend fun getReviewList(
        travelId: String,
        sortType: String,
        page: Int,
        size: Int,
        sort: List<String>
    ): BaseResponse<ReviewsResponse> {
        return retrofitInstance.getReviewList(travelId, sortType, page, size, sort)
    }
}
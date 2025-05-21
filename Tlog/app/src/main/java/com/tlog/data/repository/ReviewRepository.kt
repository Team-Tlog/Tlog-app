package com.tlog.data.repository

import android.util.Log
import com.tlog.api.TravelApi
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.ReviewRequest
import jakarta.inject.Inject

class ReviewRepository @Inject constructor(
    private val retrofitInstance: TravelApi
) {
    suspend fun addReview(review: ReviewRequest): BaseResponse<String?>{
        val result = retrofitInstance.addReview(review)
        Log.d("ReviewRepository", "addReview: $result")
        return result
    }
}
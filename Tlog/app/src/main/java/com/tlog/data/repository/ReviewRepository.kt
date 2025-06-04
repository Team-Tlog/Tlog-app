package com.tlog.data.repository

import android.util.Log
import com.tlog.api.TravelApi
import com.tlog.data.api.BaseListResponse
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.ReviewRequest
import com.tlog.data.model.travel.DetailReview
import jakarta.inject.Inject

class ReviewRepository @Inject constructor(
    private val retrofitInstance: TravelApi
) {
    suspend fun addReview(review: ReviewRequest): BaseResponse<String?>{
        val result = retrofitInstance.addReview(review)
        Log.d("ReviewRepository", "addReview: $result")
        return result
    }

    private var page = 0
    private val pageSize = 10
    private val sort = emptyList<String>()
    private var isLastPage = false

    suspend fun getReviewList(
        travelId: String,
        sortType: String,
        page: Int,
        size: Int,
        sort: List<String>
    ): BaseListResponse<List<DetailReview>> {
        return retrofitInstance.getReviewList(travelId, sortType, page, size, sort)
    }
}
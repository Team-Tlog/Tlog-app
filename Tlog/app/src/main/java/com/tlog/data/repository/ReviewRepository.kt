package com.tlog.data.repository

import com.tlog.api.ReviewApi
import com.tlog.data.dto.request.review.ReviewRequest
import com.tlog.domain.mapper.toDomain
import com.tlog.domain.model.travel.review.ReviewSummary
import jakarta.inject.Inject

class ReviewRepository @Inject constructor(
    private val retrofitInstance: ReviewApi
) {
    suspend fun addReview(review: ReviewRequest) {
        retrofitInstance.addReview(review)
    }

    suspend fun getReviewList(
        travelId: String,
        sortType: String,
        page: Int,
        size: Int,
        sort: List<String>
    ): Pair<ReviewSummary, Boolean> {
        val response = retrofitInstance.getReviews(travelId, sortType, page, size, sort)

        return response.data.toDomain() to response.data.reviews.last
    }
}

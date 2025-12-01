package com.tlog.data.model.response.review

data class ReviewsResponse(
    val ratingDistribution: Map<String, Int>,
    val reviews: Reviews
)

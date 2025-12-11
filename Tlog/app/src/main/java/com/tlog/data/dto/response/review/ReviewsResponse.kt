package com.tlog.data.dto.response.review

data class ReviewsResponse(
    val ratingDistribution: Map<String, Int>,
    val reviews: ReviewsDto
)

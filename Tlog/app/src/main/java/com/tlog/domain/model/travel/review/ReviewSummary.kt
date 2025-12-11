package com.tlog.domain.model.travel.review

data class ReviewSummary(
    val ratingDistribution: Map<String, Int>,
    val reviews: List<Review>
)
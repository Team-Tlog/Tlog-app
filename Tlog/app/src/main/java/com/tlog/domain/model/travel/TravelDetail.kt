package com.tlog.domain.model.travel

import com.tlog.domain.model.travel.review.Review

data class TravelDetail(
    val id: String,
    val name: String,
    val address: String,
    val city: String,
    val description: String,
    val district: String,
    val ratingSum: Int,
    val reviewCount: Int,
    val averageRating: Double,
    val imageUrl: String,
    val tags: List<String>,
    val ratingDistribution: Map<String, Int>,
    val reviews: List<Review>,
    val relatedTravels: List<MinimalTravel>
)

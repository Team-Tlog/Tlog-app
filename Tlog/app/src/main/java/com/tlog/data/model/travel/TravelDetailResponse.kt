package com.tlog.data.model.travel

import com.tlog.data.model.share.Tag

data class TravelDetailResponse(
    val id: String,
    val name: String,
    val address: String,
    val location: Location,
    val city: String,
    val district: String,
    val hasParking: Boolean,
    val petFriendly: Boolean,
    val ratingSum: Int,
    val reviewCount: Int,
    val averageRating: Double,
    val imageUrl: String,
    val topTags: List<TagCount>,
    val ratingDistribution: Map<String, Int>,
    val top2Reviews: List<DetailReview>,
    val relatedDestinations: List<MinimalTravel>
)

data class MinimalTravel(
    val destinationId: String,
    val name: String,
    val imageUrl: String,
    val description: String,
    val customTags: List<TagCount>
)

data class DetailReview(
    val id: String,
    val userId: String,
    val username: String,
    val userProfileImageUrl: String,
    val rating: Int,
    val content: String,
    val reviewImageUrl: List<String>,
    val createdAt: String
)
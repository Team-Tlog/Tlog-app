package com.tlog.data.dto.response.travel

import com.tlog.data.dto.share.LocationDto
import com.tlog.data.dto.share.TagCountDto
import com.tlog.data.dto.travel.MinimalTravel
import com.tlog.data.dto.travel.Review

data class TravelDetailResponse(
    val id: String,
    val name: String,
    val address: String,
    val location: LocationDto,
    val city: String,
    val description: String,
    val district: String,
    val hasParking: Boolean,
    val petFriendly: Boolean,
    val ratingSum: Int,
    val reviewCount: Int,
    val averageRating: Double,
    val imageUrl: String,
    val topTags: List<TagCountDto>,
    val ratingDistribution: Map<String, Int>,
    val top2Reviews: List<Review>,
    val relatedDestinations: List<MinimalTravel>
)

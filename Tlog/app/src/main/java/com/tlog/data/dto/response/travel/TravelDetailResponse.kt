package com.tlog.data.dto.response.travel

import com.tlog.data.dto.share.LocationDto
import com.tlog.data.dto.share.TagCountDto
import com.tlog.data.dto.travel.MinimalTravelDto
import com.tlog.data.dto.travel.ReviewDto

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
    val top2Reviews: List<ReviewDto>,
    val relatedDestinations: List<MinimalTravelDto>
)

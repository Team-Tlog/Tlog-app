package com.tlog.data.model.response.travel

import com.tlog.data.model.share.Location
import com.tlog.data.model.share.TagCount
import com.tlog.data.model.travel.MinimalTravel
import com.tlog.data.model.travel.Review

data class TravelDetailResponse(
    val id: String,
    val name: String,
    val address: String,
    val location: Location,
    val city: String,
    val description: String,
    val district: String,
    val hasParking: Boolean,
    val petFriendly: Boolean,
    val ratingSum: Int,
    val reviewCount: Int,
    val averageRating: Double,
    val imageUrl: String,
    val topTags: List<TagCount>,
    val ratingDistribution: Map<String, Int>,
    val top2Reviews: List<Review>,
    val relatedDestinations: List<MinimalTravel>
)

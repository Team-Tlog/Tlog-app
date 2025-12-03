package com.tlog.data.dto.request.travel

import com.tlog.data.dto.share.LocationDto

data class AddTravelRequest(
    val creater: String,
    val name: String,
    val address: String,
    val location: LocationDto,
    val city: String,
    val district: String,
    val hasParking: Boolean,
    val petFriendly: Boolean,
    val imageUrl: String,
    val description: String,
    val customTags: List<String>
)

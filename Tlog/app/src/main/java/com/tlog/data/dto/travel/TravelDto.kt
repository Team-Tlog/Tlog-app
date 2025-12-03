package com.tlog.data.dto.travel

import com.tlog.data.dto.share.LocationDto

data class TravelDto(
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

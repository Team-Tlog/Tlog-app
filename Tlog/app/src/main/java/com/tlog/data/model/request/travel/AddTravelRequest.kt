package com.tlog.data.model.request.travel

import com.tlog.data.model.share.Location

data class AddTravelRequest(
    val creater: String,
    val name: String,
    val address: String,
    val location: Location,
    val city: String,
    val district: String,
    val hasParking: Boolean,
    val petFriendly: Boolean,
    val imageUrl: String,
    val description: String,
    val customTags: List<String>
)

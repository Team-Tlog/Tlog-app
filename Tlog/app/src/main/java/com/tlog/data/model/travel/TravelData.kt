package com.tlog.ui.api.travel

data class TravelData(
    val name: String,
    val address: String,
    val location: Location,
    val rating: Int,
    val city: String,
    val hasParking: Boolean,
    val petFriendly: Boolean,
    val tags: List<Tag>
)
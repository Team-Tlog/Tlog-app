package com.tlog.data.model.travel

import com.tlog.ui.api.travel.Location
import com.tlog.ui.api.travel.Tag

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
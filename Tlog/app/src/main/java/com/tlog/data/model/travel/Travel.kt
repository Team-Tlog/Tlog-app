package com.tlog.data.model.travel

import com.tlog.data.model.Location


// AddTravel
data class Travel(
    val name: String,
    val address: String,
    val location: Location,
    val city: String,
    val district: String,
    val hasParking: Boolean,
    val petFriendly: Boolean,
    val imageUri: String,
    val description: String,
    val customTags: List<String>
)
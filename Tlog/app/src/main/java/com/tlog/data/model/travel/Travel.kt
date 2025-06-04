package com.tlog.data.model.travel

import com.tlog.data.model.Location
import com.tlog.data.model.share.Tag

// AddTravel
data class Travel(
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

data class ShopCart(
    val id: String,
    val name: String,
    val imageUrl: String,
    val description: String,
    val tagCountList: List<TagCount>
)

data class SearchTravel(
    val id: String,
    val name: String,
    val city: String,
    val location: Location,
    val reviewCount: Int,
    val averageRating: Double,
    val imageUrl: String?,
    val tagCountList: List<TagCount> = emptyList(),
)

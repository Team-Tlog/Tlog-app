package com.tlog.data.model.response.travel

import com.tlog.data.model.share.Location
import com.tlog.data.model.share.TagCount

data class TravelDestination(
    val id: String,
    val name: String,
    val city: String,
    val location: Location,
    val reviewCount: Int,
    val averageRating: Double,
    val imageUrl: String,
    val tagCountList: List<TagCount>
)

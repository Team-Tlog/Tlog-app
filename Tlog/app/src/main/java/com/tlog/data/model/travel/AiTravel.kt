package com.tlog.data.model.travel

import com.tlog.data.model.share.Location
import com.tlog.data.model.share.TagCount

data class AiTravel(
    val id: String,
    val name: String,
    val description: String,
    val city: String,
    val district: String,
    val location: Location,
    val imageUrl: String?,
    val tags: List<String>,
    val tagCountList: List<TagCount>?,
    val similarityScore: Double,
    val isFromWishlist: Boolean
)

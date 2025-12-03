package com.tlog.data.dto.response.travel

import com.tlog.data.dto.share.LocationDto
import com.tlog.data.dto.share.TagCountDto

data class AiTravel(
    val id: String,
    val name: String,
    val description: String,
    val city: String,
    val district: String,
    val location: LocationDto,
    val imageUrl: String?,
    val tags: List<String>,
    val tagCountList: List<TagCountDto>?,
    val similarityScore: Double,
    val isFromWishlist: Boolean
)
package com.tlog.data.dto.response.travel

import com.tlog.data.dto.share.LocationDto
import com.tlog.data.dto.share.TagCountDto

data class TravelDestinationDto(
    val id: String,
    val name: String,
    val city: String,
    val location: LocationDto,
    val reviewCount: Int,
    val averageRating: Double,
    val imageUrl: String,
    val tagCountList: List<TagCountDto>
)

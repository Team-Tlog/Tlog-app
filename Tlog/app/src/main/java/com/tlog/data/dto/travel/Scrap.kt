package com.tlog.data.dto.travel

import com.tlog.data.dto.share.LocationDto
import com.tlog.data.dto.share.TagCountDto

data class Scrap(
    val id: String,
    val name: String,
    val imageUrl: String,
    val description: String,
    val tagCountList: List<TagCountDto>,
    val location: LocationDto
)

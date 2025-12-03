package com.tlog.data.dto.team

import com.tlog.data.dto.share.LocationDto
import com.tlog.data.dto.share.TagCountDto

data class WishlistItem(
    val id: String,
    val name: String,
    val location: LocationDto,
    val imageUrl: String,
    val description: String,
    val tagCountList: List<TagCountDto>
)

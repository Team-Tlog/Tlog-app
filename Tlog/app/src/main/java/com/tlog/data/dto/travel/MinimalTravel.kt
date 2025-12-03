package com.tlog.data.dto.travel

import com.tlog.data.dto.share.TagCountDto


data class MinimalTravel(
    val destinationId: String,
    val name: String,
    val imageUrl: String,
    val description: String,
    val customTags: List<TagCountDto>? = emptyList()
)

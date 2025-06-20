package com.tlog.data.model.travel

import com.tlog.data.model.share.TagCount


data class MinimalTravel(
    val destinationId: String,
    val name: String,
    val imageUrl: String,
    val description: String,
    val customTags: List<TagCount>? = emptyList()
)
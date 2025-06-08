package com.tlog.data.api

import com.tlog.data.model.Location

data class ScrapDestinationResponse(
    val id: String,
    val name: String,
    val imageUrl: String,
    val description: String,
    val tagCountList: List<TagCount>,

    // 추가 되어야 함
    val location: Location
)

data class TagCount(
    val tagName: String,
    val count: Int
)
package com.tlog.data.api

data class ScrapDestinationResponse(
    val id: String,
    val name: String,
    val imageUrl: String,
    val description: String,
    val tagCountList: List<TagCount>
)

data class TagCount(
    val tagName: String,
    val count: Int
)
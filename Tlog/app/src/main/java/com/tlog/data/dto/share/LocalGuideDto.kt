package com.tlog.data.dto.share

data class LocalGuideDto(
    val title: String,
    val imageUrl: String,
    val description: String,
    val infoUrl: String,
    val property: List<String>
)

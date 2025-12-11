package com.tlog.data.dto.share

data class BannerDto(
    val id: String,
    val imageUrl: String,
    val title: String,
    val hashtags: List<String>
)

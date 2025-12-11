package com.tlog.domain.model.share

data class Banner(
    val id: String,
    val imageUrl: String,
    val title: String,
    val hashtags: List<String>
)

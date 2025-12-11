package com.tlog.domain.model.share

data class RecommendPost(
    val id: String,
    val title: String,
    val description: String,
    val imageUrls: List<String>
)

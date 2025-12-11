package com.tlog.domain.model.share

data class LocalGuide(
    val title: String,
    val imageUrl: String,
    val infoUrl: String,
    val description: String,
    val hashTags: List<String>
)

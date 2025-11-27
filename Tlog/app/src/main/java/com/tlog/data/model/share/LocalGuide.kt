package com.tlog.data.model.share

data class LocalGuide(
    val title: String,
    val imageUrl: String,
    val description: String,
    val infoUrl: String,
    val property: List<String>
)

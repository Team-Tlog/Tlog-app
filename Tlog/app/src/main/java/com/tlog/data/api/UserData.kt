package com.tlog.data.api


data class ProfileImageRequest(
    val imageUrl: String
)

data class FeedbackRequest(
    val title: String,
    val content: String,
    val refImageUrls: List<String>
)
package com.tlog.data.dto.request.auth

data class FeedbackRequest(
    val title: String,
    val content: String,
    val refImageUrls: List<String>
)

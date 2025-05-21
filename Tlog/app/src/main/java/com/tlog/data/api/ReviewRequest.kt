package com.tlog.data.api

data class ReviewRequest(
    val userId: String,
    val destinationId: String,
    val username: String,
    val rating: Int,
    val content: String,
    val imageUrlList: List<String>,
    val customTagNames: List<String>
)
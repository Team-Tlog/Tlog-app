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

data class ReviewResponse(
    val id: String,
    val userId: String,
    val username: String,
    val userProfileImageUrl: String,
    val rating: Int,
    val content: String, // title
    val reviewImageUrl: List<String>,
    val createdAt: String
)
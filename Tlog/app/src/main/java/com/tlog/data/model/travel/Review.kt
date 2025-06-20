package com.tlog.data.model.travel

data class Review(
    val id: String,
    val userId: String,
    val username: String,
    val userProfileImageUrl: String,
    val rating: Int,
    val content: String,
    val reviewImageUrl: List<String>,
    val createdAt: String
)
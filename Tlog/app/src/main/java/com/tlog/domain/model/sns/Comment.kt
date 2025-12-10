package com.tlog.domain.model.sns

data class Comment(
    val userId: String,
    val userName: String,
    val comment: String,
    val userProfileImageUrl: String
)

package com.tlog.domain.model.sns

data class SnsPost (
    val id: String,
    val postLikeCount: Int,
    val authorId: String,
    val authorName: String,
    val authorProfileImageUrl: String,
    val contentImageUrls: List<String>,
    val content: String,
    val comments: List<Comment>
)

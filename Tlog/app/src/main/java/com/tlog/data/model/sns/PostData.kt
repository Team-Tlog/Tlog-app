package com.tlog.data.model.sns


data class PostData(
    val id: String,
    val userId: String,
    val likesCount: Int,
    val isLiked: Boolean,
    val isUserFollowing: Boolean,
    val commentsPreview: List<Comment> = emptyList(),
    val courseTitles: List<String> = emptyList()
)
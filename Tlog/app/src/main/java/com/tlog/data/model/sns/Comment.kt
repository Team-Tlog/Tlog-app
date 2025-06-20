package com.tlog.data.model.sns

data class Comment(
    val replyId: String,
    val content: String,
    val nestedReplyCount: Int,
    val authorId: String,
    val authorName: String,
    val authorProfileImageUrl: String
)
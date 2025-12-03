package com.tlog.data.dto.sns

data class CommentDto(
    val replyId: String,
    val content: String,
    val nestedReplyCount: Int,
    val authorId: String,
    val authorName: String,
    val authorProfileImageUrl: String
)

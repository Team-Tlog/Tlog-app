package com.tlog.data.dto.response.sns

import com.tlog.data.dto.sns.CommentDto

data class SnsPostDto(
    val postId: String,
    val postLikeCount: Int,
    val postLinkCode: String,
    val courseId: String,
    val courseDistrics: List<String>,
    val authorId: String,
    val authorName: String,
    val authorProfileImageUrl: String? = "",
    val contentImageUrls: List<String>,
    val content: String,
    val replies: List<CommentDto>
)
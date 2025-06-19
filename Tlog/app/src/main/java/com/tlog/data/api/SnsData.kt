package com.tlog.data.api

import com.tlog.data.model.sns.Comment

data class UpdateSnsIdRequest(
    val snsId: String
)

data class SnsDescription(
    val description: String
)

data class CreateCommentRequest(
    val author: String,
    val content: String
)

data class SnsPost(
    val postId: String,
    val postLikeCount: Int,
    val postLinkCode: String,
    val courseId: String,
    val courseDistrics: List<String>,
    val authorId: String,
    val authorName: String,
    val authorProfileImageUrl: String,
    val contentImageUrls: List<String>,
    val content: String,
    val replies: List<Comment>
)

data class SnsPostPreview(
    val postId: String,
    val previewImageUrl: String
)

data class StatusMessage(
    val status: Boolean,
    val message: String
)

data class SnsUser(
    val uuid: String,
    val name: String,
    val snsName: String,
    val profileImageUrl: String,
    val tbtiValue: Int
)


data class SnsUserProfile(
    val username: String,
    val profileImageUrl: String?,
    val snsDescription: String?,
    val postCount: Int,
    val followerCount: Int,
    val followingCount: Int,
    val posts: BaseListPage<List<SnsPostPreview>>
)

data class FollowRequest(
    val from_userId: String,
    val to_userId: String
)
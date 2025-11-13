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

data class ChatRoom(
    val chatRoomId: Long,
    val lastMessageContent: String?,
    val lastMessageSentAt: String?,
    val countChatRoomUsers: Int,
    val unreadCount: Int
)

data class ChatMessageHistory(
    val id: Long,
    val chatRoomId: Long,
    val senderId: String,
    val senderName: String,
    val content: String,
    val sendAt: String,
    val unreadCount: Int
)

data class ChatMessageHistoryResponse(
    val messages: List<ChatMessageHistory>,
    val nextCursor: Long?,
    val hasNext: Boolean
)

data class ChatMessageReadDto(
    val readerId: String,
    val messageId: Long
)
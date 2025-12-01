package com.tlog.data.model.response.sns

data class ChatRoom(
    val chatRoomId: Long,
    val lastMessageContent: String?,
    val lastMessageSentAt: String?,
    val countChatRoomUsers: Int,
    val unreadCount: Int
)

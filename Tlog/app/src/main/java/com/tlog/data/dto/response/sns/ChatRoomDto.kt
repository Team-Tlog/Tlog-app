package com.tlog.data.dto.response.sns

data class ChatRoomDto(
    val chatRoomId: Long,
    val lastMessageContent: String?,
    val lastMessageSentAt: String?,
    val countChatRoomUsers: Int,
    val unreadCount: Int
)

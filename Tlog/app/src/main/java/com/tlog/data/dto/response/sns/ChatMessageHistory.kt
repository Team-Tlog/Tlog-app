package com.tlog.data.dto.response.sns

data class ChatMessageHistory(
    val id: Long,
    val chatRoomId: Long,
    val senderId: String,
    val senderName: String,
    val content: String,
    val sendAt: String,
    val unreadCount: Int
)

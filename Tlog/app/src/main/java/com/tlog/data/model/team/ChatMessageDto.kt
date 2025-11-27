package com.tlog.data.model.team

data class ChatMessageDto(
    val messageId: Long,
    val chatRoomId: Long,
    val senderId: String,
    val senderName: String,
    val content: String,
    val sendAt: String,
    var unreadCount: Int = 0
)

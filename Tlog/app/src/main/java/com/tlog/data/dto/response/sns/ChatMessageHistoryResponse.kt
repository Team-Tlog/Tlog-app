package com.tlog.data.dto.response.sns

data class ChatMessageHistoryResponse(
    val messages: List<ChatMessageHistory>,
    val nextCursor: Long?,
    val hasNext: Boolean
)

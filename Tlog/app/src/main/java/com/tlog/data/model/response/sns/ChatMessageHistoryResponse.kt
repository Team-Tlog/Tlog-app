package com.tlog.data.model.response.sns

data class ChatMessageHistoryResponse(
    val messages: List<ChatMessageHistory>,
    val nextCursor: Long?,
    val hasNext: Boolean
)

package com.tlog.data.model.notification

import kotlinx.serialization.Serializable


@Serializable
data class NotificationItem(
    val content: String,
    val notificationType: String,
    val linkType: String? = null,
    val linkAddress: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)

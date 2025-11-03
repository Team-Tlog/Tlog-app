package com.tlog.data.model.notification

import kotlinx.serialization.Serializable

@Serializable
data class TSnsNotificationItem(
    val content: String,
    val notificationType: String,
    val link: LinkType? = null,
    val linkAddress: String? = null,
    val actorId: String? = null,
    val actorImage: String? = null,
    val objectId: String? = null,
    val objectImage: String? = null,
    val isFollowing: Boolean? = null,
    val timestamp: Long = System.currentTimeMillis()
//    val isRead: Boolean = false
)
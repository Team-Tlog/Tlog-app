package com.tlog.data.model.notification

data class TsnsNotificationData(
    val userName: String,
    val action: String,
    val time: String,
    val showFollowButton: Boolean
)
package com.tlog.data.model.notification

data class TsnsNotificationData(
    val userName: String,
    val action: String,
    val time: String,
    val showFollowButton: Boolean,
    val userProfileImageUrl: String, // 왼쪽 프로필 사진 URL
    val postThumbnailImageUrl: String? // 게시글 썸네일 사진 URL (nullable)
)
package com.tlog.viewmodel.share

import androidx.lifecycle.ViewModel
import com.tlog.data.model.notification.TsnsNotificationData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TsnsNotificationViewModel : ViewModel() {

    private val _tsnsNotifications = MutableStateFlow<List<TsnsNotificationData>>(emptyList())
    val tsnsNotifications: StateFlow<List<TsnsNotificationData>> = _tsnsNotifications

    init {
        fetchTsnsNotifications()
    }

    private fun fetchTsnsNotifications() {
        _tsnsNotifications.value = listOf(
            TsnsNotificationData(
                userName = "Tlog",
                action = "님이 회원님의 게시글에 댓글을 남겼습니다",
                time = "방금 전",
                showFollowButton = false,
                userProfileImageUrl = "",
                postThumbnailImageUrl = ""
            ),
            TsnsNotificationData(
                userName = "Tlog",
                action = "님이 회원님의 게시글에 좋아요를 남겼습니다",
                time = "방금 전",
                showFollowButton = false,
                userProfileImageUrl = "",
                postThumbnailImageUrl = ""
            ),
            TsnsNotificationData(
                userName = "Tlog",
                action = "님이 회원님을 팔로우하기 시작했습니다",
                time = "방금 전",
                showFollowButton = true,          // 맞팔이면 postThumbnailImageUrl 필요 없음
                userProfileImageUrl = "",
                postThumbnailImageUrl = null       // null
            ),
            TsnsNotificationData(
                userName = "Tlog",
                action = "님이 회원님의 게시글에 좋아요를 남겼습니다",
                time = "방금 전",
                showFollowButton = false,
                userProfileImageUrl = "",
                postThumbnailImageUrl = ""
            ),
            TsnsNotificationData(
                userName = "Tlog",
                action = "님이 회원님의 게시글에 댓글을 남겼습니다",
                time = "방금 전",
                showFollowButton = false,
                userProfileImageUrl = "",
                postThumbnailImageUrl = ""
            )
        )
    }
}

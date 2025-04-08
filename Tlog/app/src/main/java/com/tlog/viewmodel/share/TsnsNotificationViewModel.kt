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
            TsnsNotificationData("Tlog", "회원님의 게시글에 댓글을 남겼습니다", "방금 전", false),
            TsnsNotificationData("Tlog", "회원님의 게시글에 좋아요를 남겼습니다", "방금 전", false),
            TsnsNotificationData("Tlog", "회원님을 팔로우하기 시작했습니다", "방금 전", true),
            TsnsNotificationData("Tlog", "회원님의 게시글에 좋아요를 남겼습니다", "방금 전", false),
            TsnsNotificationData("Tlog", "회원님의 게시글에 댓글을 남겼습니다", "방금 전", false),
        )
    }
}

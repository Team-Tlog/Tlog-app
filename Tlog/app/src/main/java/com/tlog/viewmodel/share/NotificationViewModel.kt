package com.tlog.viewmodel.share

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.tlog.data.model.notification.NotificationData

class NotificationViewModel : ViewModel() {

    private val _notifications = MutableStateFlow<List<NotificationData>>(emptyList())
    val notifications: StateFlow<List<NotificationData>> = _notifications

    init {
        fetchNotifications()
    }

    private fun fetchNotifications() {
        _notifications.value = listOf(
            NotificationData(content = "소식기 내용소식기 내용 1", date = "03/25"),
            NotificationData(content = "소식기 내용소식기 내용 2", date = "03/15"),
            NotificationData(content = "소식기 내용소식기 내용 3", date = "03/10"),
        )
    }
}

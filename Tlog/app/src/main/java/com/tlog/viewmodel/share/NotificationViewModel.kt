package com.tlog.viewmodel.share

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.tlog.data.model.notification.NotificationData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NotificationViewModel @Inject constructor (): ViewModel() {
    init {
        //fetchNotifications()
    }


    private val _notifications = MutableStateFlow<List<NotificationData>>(listOf(
        NotificationData(content = "소식기 내용소식기 내용 1 소식기 내용소식기 내용 1 소식기 내용소식기 내용 1 소식기 내용소식기 내용 1 소식기 내용소식기 내용 1", date = "03/25"),
        NotificationData(content = "소식기 내용소식기 내용 2 소식기 내용소식기 내용 2 소식기 내용소식기 내용 2 소식기 내용소식기 내용 2 소식기 내용소식기 내용 2", date = "03/15"),
        NotificationData(content = "소식기 내용소식기 내용 3 소식기 내용소식기 내용 3 소식기 내용소식기 내용 3 소식기 내용소식기 내용 3 소식기 내용소식기 내용 3", date = "03/10"),
    ))
    val notifications: StateFlow<List<NotificationData>> = _notifications

    private val _selectedTab = mutableStateOf("새 소식")
    val selectedTab = _selectedTab


    fun updateSelectedTab(tab: String) {
        _selectedTab.value = tab
    }



    private fun fetchNotifications() {
    }
}

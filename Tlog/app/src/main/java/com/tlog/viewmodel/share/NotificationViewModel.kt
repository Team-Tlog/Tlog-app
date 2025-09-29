package com.tlog.viewmodel.share

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.tlog.data.local.FollowManager
import com.tlog.data.local.NotificationManager
import com.tlog.viewmodel.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.tlog.data.model.notification.NotificationItem
import com.tlog.data.model.notification.TSnsNotificationItem
import com.tlog.data.model.share.toErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationManager: NotificationManager,
    private val followManager: FollowManager
): BaseViewModel() {
    private val _tSnsNotificationList = MutableStateFlow<List< TSnsNotificationItem>>(emptyList())
    val tSnsNotificationList: StateFlow<List<TSnsNotificationItem>> = _tSnsNotificationList

    private val _notificationList = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notificationList: StateFlow<List<NotificationItem>> = _notificationList

    private val _selectedTab = mutableStateOf("새 소식")
    val selectedTab = _selectedTab

    val followingList = followManager.followingList

    init {
        fetchNotifications()
        fetchTSnsNotifications()
    }


    fun updateSelectedTab(tab: String) {
        _selectedTab.value = tab
    }


    private fun fetchNotifications() {
        viewModelScope.launch {
            notificationManager.getNotificationList()
                .collect { notificationList ->
                    _notificationList.value = notificationList
                }
        }
    }

    private fun fetchTSnsNotifications() {
        viewModelScope.launch {
            notificationManager.getTSnsNotificationList()
                .collect { tSnsNotificationList ->
                    _tSnsNotificationList.value = tSnsNotificationList
                }
        }
    }

    fun followUser(userId: String) {
        launchSafeCall(
            action = {
                followManager.followUser(userId)
            },
            onError = { errorMessage ->
                showToast(errorMessage)
            }
        )
    }
}

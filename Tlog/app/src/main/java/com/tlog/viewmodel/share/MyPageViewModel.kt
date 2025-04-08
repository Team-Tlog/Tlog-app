package com.tlog.viewmodel.share

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


class MyPageViewModel: ViewModel() {
    private val _notification = mutableStateOf(true)
    val notification: State<Boolean> = _notification

    fun changeNotification() {
        _notification.value = !_notification.value
    }
}
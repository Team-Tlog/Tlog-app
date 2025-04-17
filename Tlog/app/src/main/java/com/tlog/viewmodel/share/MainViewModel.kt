package com.tlog.viewmodel.share

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val _userId = mutableStateOf<String?>(null)
    val userId: State<String?> = _userId

    fun set(userId: String?) {
        _userId.value = userId
    }
}
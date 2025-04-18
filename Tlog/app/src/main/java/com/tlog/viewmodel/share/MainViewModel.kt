package com.tlog.viewmodel.share

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _userId = mutableStateOf<String?>(null)
    val userId: State<String?> = _userId

    private val _accessToken = mutableStateOf<String?>(null)
    val accessToken: State<String?> = _accessToken

    private val _refreshToken = mutableStateOf<String?>(null)
    val refreshToken: State<String?> = _refreshToken

    fun set(userId: String?, accessToken: String?, refreshToken: String?) {
        _userId.value = userId
        _accessToken.value = accessToken
        _refreshToken.value = refreshToken
    }
}
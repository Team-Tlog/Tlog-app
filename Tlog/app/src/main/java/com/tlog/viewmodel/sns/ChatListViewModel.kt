package com.tlog.viewmodel.sns

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.SnsApi
import com.tlog.data.model.response.sns.ChatRoom
import com.tlog.data.local.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val snsApi: SnsApi,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _chatRoomList = MutableStateFlow<List<ChatRoom>>(emptyList())
    val chatRoomList: StateFlow<List<ChatRoom>> get() = _chatRoomList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    init {
        loadChatList()
    }

    fun loadChatList() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val userId = userPreferences.getUserId()
                if (userId != null) {
                    val response = snsApi.getChatList(userId)
                    if (response.status == 200) {
                        _chatRoomList.value = response.data ?: emptyList()
                        Log.d("ChatListViewModel", "Chat rooms loaded: ${_chatRoomList.value.size}")
                    } else {
                        _errorMessage.value = response.message
                        Log.e("ChatListViewModel", "Error: ${response.message}")
                    }
                } else {
                    _errorMessage.value = "사용자 ID를 찾을 수 없습니다."
                    Log.e("ChatListViewModel", "User ID is null")
                }
            } catch (e: Exception) {
                _errorMessage.value = "네트워크 오류가 발생했습니다."
                Log.e("ChatListViewModel", "Exception: ${e.message}", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
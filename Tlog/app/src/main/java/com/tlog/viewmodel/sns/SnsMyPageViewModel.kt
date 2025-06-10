package com.tlog.viewmodel.sns

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.SnsUserProfile
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.local.FollowManager
import com.tlog.data.repository.SnsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SnsMyPageViewModel @Inject constructor(
    private val repository: SnsRepository,
    private val followManager: FollowManager,
    tokenProvider: TokenProvider
): ViewModel() {
    private val _userId = mutableStateOf<String?>(null)
    val userId: State<String?> = _userId

    init {
        _userId.value = tokenProvider.getUserId()

    }


    private var _userProfileInfo = MutableStateFlow<SnsUserProfile?>(null)
    val userProfileInfo: StateFlow<SnsUserProfile?> = _userProfileInfo





    fun getUserProfile(userId: String) {
        viewModelScope.launch {
            try {
                val result = repository.getUserProfile(userId!!)

                when (result.status) {
                    200 -> {
                        _userProfileInfo.value = result.data
                    }
                    else -> {}
                }
            } catch (e: Exception) {
                Log.d("getUserProfile", e.toString())
            }
        }
    }


    // 페이지가 없음
    fun updateSnsDescription(description: String) {
        viewModelScope.launch {
            try {
                val result = repository.updateSnsDescription(description)
            } catch (e: Exception) {
                Log.d("updateSnsDescription", e.toString())
            }
        }
    }

    // 팔로우 매니저
    val followingList: StateFlow<Set<String>> = followManager.followingList

    fun followUser(toUserId: String) {
        viewModelScope.launch {
            try {
                followManager.followUser(toUserId)
            } catch (e: Exception) {
                Log.d("SnsViewModel", e.message.toString())
            }
        }
    }
}
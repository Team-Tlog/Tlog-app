package com.tlog.viewmodel.sns

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.api.SnsUserProfile
import com.tlog.data.local.FollowManager
import com.tlog.data.model.share.toErrorMessage
import com.tlog.data.repository.SnsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class SnsMyPageViewModel @Inject constructor(
    private val repository: SnsRepository,
    private val followManager: FollowManager,
    tokenProvider: TokenProvider
): ViewModel() {
    sealed class UiEvent {
        data class Error(val message: String): UiEvent()
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
                _userProfileInfo.value = repository.getUserProfile(userId).data
            } catch (e: HttpException) {
                _eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
            }
        }
    }


    // 페이지가 없음
    fun updateSnsDescription(description: String) {
        viewModelScope.launch {
            try {
                val result = repository.updateSnsDescription(description)
            } catch (e: HttpException) {
                _eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
            }
        }
    }

    // 팔로우 매니저
    val followingList: StateFlow<Set<String>> = followManager.followingList

    fun followUser(toUserId: String) {
        viewModelScope.launch {
            try {
                followManager.followUser(toUserId)
            } catch (e: HttpException) {
                _eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
            }
        }
    }
}
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
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class SnsMyPageViewModel @Inject constructor(
    private val repository: SnsRepository,
    private val followManager: FollowManager,
    tokenProvider: TokenProvider
): ViewModel() {
    sealed interface UiEvent {
        data class Navigate(val target: Screen, val clearBackStack: Boolean = false): UiEvent
        data class ShowToast(val message: String): UiEvent
    }

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

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
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            } catch (e: Exception) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            }
        }
    }


    // 페이지가 없음
    fun updateSnsDescription(description: String) {
        viewModelScope.launch {
            try {
                val result = repository.updateSnsDescription(description)
            } catch (e: HttpException) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            } catch (e: Exception) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
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
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            } catch (e: Exception) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            }
        }
    }

    fun navToSnsPostDetail(postId: String) {
        _uiEvent.trySend(UiEvent.Navigate(Screen.SnsPostDetail(postId)))
    }
}
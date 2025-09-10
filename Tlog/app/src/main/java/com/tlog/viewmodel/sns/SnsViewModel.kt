package com.tlog.viewmodel.sns

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.api.SnsPost
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
class SnsViewModel @Inject constructor(
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

    private var userId: String? = ""

    private var lastPostId: String? = null
    private var size = 10

    private var _postList = MutableStateFlow(listOf<SnsPost>())
    val postList: StateFlow<List<SnsPost>> = _postList

    val followingList: StateFlow<Set<String>> = followManager.followingList

    init {
        userId = tokenProvider.getUserId()
        getSnsPost()
    }




    fun getSnsPost() {
        viewModelScope.launch {
            try {
                val result = repository.getFollowingPostList(lastPostId = lastPostId, size = size)
                _postList.value = result.data.content
                lastPostId = _postList.value[result.data.size - 1].postId
            } catch(e: HttpException) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            } catch(e: Exception) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            }
        }

    }

    fun followUser(toUserId: String) {
        viewModelScope.launch {
            try {
                followManager.followUser(toUserId)
            } catch(e: HttpException) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            } catch(e: Exception) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            }
        }
    }

    fun navToSnsMyPage(userId: String) {
        _uiEvent.trySend(UiEvent.Navigate(Screen.SnsMyPage(userId)))
    }

    fun navToSnsPostDetail(postId: String) {
        _uiEvent.trySend(UiEvent.Navigate(Screen.SnsPostDetail(postId)))
    }
}

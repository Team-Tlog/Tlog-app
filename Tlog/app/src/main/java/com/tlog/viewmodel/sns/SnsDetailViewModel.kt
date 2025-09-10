package com.tlog.viewmodel.sns

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
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
class SnsDetailViewModel @Inject constructor(
    private val repository: SnsRepository,
    private val followManager: FollowManager,
    tokenProvider: TokenProvider
) : ViewModel() {

    sealed interface UiEvent {
        data class Navigate(val target: Screen, val clearBackStack: Boolean = false): UiEvent
        data class ShowToast(val message: String) : UiEvent
    }

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    var userId: String? = ""

    init {
        userId = tokenProvider.getUserId()
    }


    private var _post = MutableStateFlow<SnsPost?>(null)
    val post: StateFlow<SnsPost?> = _post

    private var _comment = mutableStateOf("")
    val comment: State<String> = _comment

    fun getPostDetail(postId: String) {
        viewModelScope.launch {
            try {
                val result = repository.getPost(postId)

                _post.value = result.data
            } catch (e: HttpException) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            } catch (e: Exception) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            }
        }
    }

    fun addComment() {
        viewModelScope.launch {
            try {
                repository.createComment(postId = post.value!!.postId, author = userId!!, content = comment.value)

                _comment.value = ""
                getPostDetail(post.value!!.postId)
            } catch (e: Exception) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            } catch (e: HttpException) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            }
        }
    }

    fun updateComment(value: String) {
        _comment.value = value
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

    fun navToSnsMyPage(userId: String) {
        _uiEvent.trySend(UiEvent.Navigate(Screen.SnsMyPage(userId)))
    }
}
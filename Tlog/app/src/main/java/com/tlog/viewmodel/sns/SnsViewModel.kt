package com.tlog.viewmodel.sns

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.api.SnsPost
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
class SnsViewModel @Inject constructor(
    private val repository: SnsRepository,
    private val followManager: FollowManager,
    tokenProvider: TokenProvider
): ViewModel() {
    sealed class UiEvent {
        data class Error(val message: String): UiEvent()
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
                _eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
            } catch(e: Exception) {
                _eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
            }
        }

    }

    fun followUser(toUserId: String) {
        viewModelScope.launch {
            try {
                followManager.followUser(toUserId)
            } catch(e: HttpException) {
                _eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
            } catch(e: Exception) {
                _eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
            }
        }
    }

}

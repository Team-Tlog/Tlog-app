package com.tlog.viewmodel.sns

import com.tlog.data.local.TokenProvider
import com.tlog.data.dto.response.sns.SnsPostDto
import com.tlog.data.local.FollowManager
import com.tlog.data.repository.SnsRepository
import com.tlog.domain.model.sns.SnsPost
import com.tlog.ui.navigation.Screen
import com.tlog.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class SnsViewModel @Inject constructor(
    private val repository: SnsRepository,
    private val followManager: FollowManager,
    tokenProvider: TokenProvider
): BaseViewModel() {

    private var userId: String? = ""

    private var lastPostId: String? = null
    private var size = 10

    private var _postList = MutableStateFlow<List<SnsPost>>(emptyList())
    val postList = _postList.asStateFlow()

    val followingList: StateFlow<Set<String>> = followManager.followingList

    init {
        userId = tokenProvider.getUserId()
        getSnsPost()
    }

    fun getSnsPost() {
        launchSafeCall(
            action = {
                val result = repository.getFollowingPostList(lastPostId = lastPostId, size = size)
                _postList.value = result
                if (_postList.value.isNotEmpty())
                    lastPostId = _postList.value[_postList.value.size - 1].id
            }
        )
    }

    fun followUser(toUserId: String) {
        launchSafeCall(
            action = {
                followManager.followUser(toUserId)
            }
        )
    }

    fun navToSnsMyPage(userId: String) {
        navigate(Screen.SnsMyPage(userId))
    }

    fun navToSnsPostDetail(postId: String) {
        navigate(Screen.SnsPostDetail(postId))
    }

    fun navToSnsSearch() {
        navigate(Screen.SnsSearch)
    }

    fun navToNotification() {
        navigate(Screen.Notification)
    }

    fun navToSnsPostWrite() {
        navigate(Screen.SnsPostWrite)
    }
}

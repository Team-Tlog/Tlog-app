package com.tlog.viewmodel.sns

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.tlog.data.local.TokenProvider
import com.tlog.data.model.response.sns.SnsPost
import com.tlog.data.local.FollowManager
import com.tlog.data.repository.SnsRepository
import com.tlog.ui.navigation.Screen
import com.tlog.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class SnsDetailViewModel @Inject constructor(
    private val repository: SnsRepository,
    private val followManager: FollowManager,
    tokenProvider: TokenProvider
) : BaseViewModel() {

    var userId: String? = ""

    init {
        userId = tokenProvider.getUserId()
    }


    private var _post = MutableStateFlow<SnsPost?>(null)
    val post: StateFlow<SnsPost?> = _post

    private var _comment = mutableStateOf("")
    val comment: State<String> = _comment

    private val _isLike = mutableStateOf(false)
    val isLike: State<Boolean> = _isLike



    fun getPostDetail(postId: String) {
        launchSafeCall(
            action = {
                val result = repository.getPost(postId)
                _post.value = result.data
            }
        )
    }

    fun addComment() {
        launchSafeCall(
            action = {
                repository.createComment(postId = post.value!!.postId, author = userId!!, content = comment.value)
                _comment.value = ""
                getPostDetail(post.value!!.postId)
            }
        )
    }

    fun updateComment(value: String) {
        _comment.value = value
    }


    // 팔로우 매니저
    val followingList: StateFlow<Set<String>> = followManager.followingList

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

    fun clickLike(postId: String) {
        _isLike.value = !_isLike.value

        postLike(postId = postId)
    }

    fun postLike(postId: String) {
        launchSafeCall(
            action = {
                repository.postLikeToggle(postId)
            }
        )
    }

    fun postReport(postId: String) {
        launchSafeCall(
            action = {
                repository.postReport(postId)
            }
        )
    }
}
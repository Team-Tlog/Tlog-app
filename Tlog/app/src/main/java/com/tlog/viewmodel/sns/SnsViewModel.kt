package com.tlog.viewmodel.sns

// ViewModel 및 데이터 모델
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.SnsPost
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.local.FollowManager
import com.tlog.data.repository.SnsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SnsViewModel @Inject constructor(
    private val repository: SnsRepository,
    private val followManager: FollowManager,
    tokenProvider: TokenProvider
): ViewModel() {
    val followingList: StateFlow<Set<String>> = followManager.followingList

    private var userId: String? = ""
    private var lastPostId: String? = null
    private var size = 10

    init {
        userId = tokenProvider.getUserId()
        getSnsPost()
    }


    private var _postList = mutableStateOf(listOf<SnsPost>())
    val postList: State<List<SnsPost>> = _postList


    fun getSnsPost() {
        viewModelScope.launch {
            try {
                val result = repository.getFollowingPostList(lastPostId = lastPostId, size = size)
                _postList.value = result.data.content
                lastPostId = _postList.value[result.data.size - 1].postId
            } catch(e: Exception) {
                Log.d("SnsViewModel", e.message.toString())
            }
        }

    }

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

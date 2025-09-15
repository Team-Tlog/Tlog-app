package com.tlog.viewmodel.sns

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.api.SnsUserProfile
import com.tlog.data.local.FollowManager
import com.tlog.data.repository.SnsRepository
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class SnsMyPageViewModel @Inject constructor(
    private val repository: SnsRepository,
    private val followManager: FollowManager,
    tokenProvider: TokenProvider
): BaseViewModel() {

    private val _userId = mutableStateOf<String?>(null)
    val userId: State<String?> = _userId

    init {
        _userId.value = tokenProvider.getUserId()
    }


    private var _userProfileInfo = MutableStateFlow<SnsUserProfile?>(null)
    val userProfileInfo: StateFlow<SnsUserProfile?> = _userProfileInfo





    fun getUserProfile(userId: String) {
        launchSafeCall(
            action = {
                _userProfileInfo.value = repository.getUserProfile(userId).data
            }
        )
    }


    // 페이지가 없음
    fun updateSnsDescription(description: String) {
        launchSafeCall(
            action = {
                repository.updateSnsDescription(description)
            }
        )
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

    fun navToSnsPostDetail(postId: String) {
        navigate(Screen.SnsPostDetail(postId))
    }
}
package com.tlog.viewmodel.share

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.local.UserPreferences
import com.tlog.data.repository.MyPageRepository
import com.tlog.data.util.FirebaseImageUploader
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject
import android.net.Uri
import androidx.core.net.toUri
import com.tlog.data.api.ProfileImageRequest
import com.tlog.data.model.user.User
import com.tlog.ui.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.String

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
    private val tokenProvider: TokenProvider,
    private val userPreferences: UserPreferences
): BaseViewModel() {


    private val _notification = mutableStateOf(true)
    val notification: State<Boolean> = _notification

    private val _userInfo: MutableState<User?> = mutableStateOf(null)
    val userInfo: State<User?> = _userInfo

    private val _image = mutableStateOf("")
    val imageUri = _image




    private val _getUserInfo = MutableStateFlow(false)
    val getUserInfo = _getUserInfo.asStateFlow()

    init {
        getUserInfo()
    }


    private fun getUserInfo() {
        launchSafeCall(
            action = {
                _userInfo.value =  myPageRepository.getUserInfo().data
                _getUserInfo.value = true
            }
        )
    }

    // 로그아웃
    fun logout() {
        val refreshToken = tokenProvider.getRefreshToken() ?: ""
        launchSafeCall(
            action = {
                myPageRepository.logout(refreshToken)
                showToast("로그아웃 성공")
                navigate(Screen.Login, true)
                userPreferences.clearTokens()
            }
        )
    }

    fun changeNotification() {
        _notification.value = !_notification.value
    }

    suspend fun imageUpload(context: Context, imageUri: Uri): String {
        return FirebaseImageUploader.uploadWebpImage(
            context,
            imageUri,
            "images/profileimage/${System.currentTimeMillis()}_${UUID.randomUUID()}.webp"
        )
    }

    fun updateProfileImage(context: Context ){
        launchSafeCall(
            action = {
                val imageUrl = imageUpload(context, imageUri.value.toUri())

                myPageRepository.updateProfileImage(
                    ProfileImageRequest(
                        imageUrl = imageUrl
                    )
                )

                getUserInfo()
                showToast("프로필 사진 변경 성공")
            }
        )
    }

    fun navToTbtiTest() {
        navigate(Screen.TbtiIntro)
    }
}

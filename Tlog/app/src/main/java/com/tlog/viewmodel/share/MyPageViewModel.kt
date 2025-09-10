package com.tlog.viewmodel.share

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.local.UserPreferences
import com.tlog.data.repository.MyPageRepository
import com.tlog.data.util.FirebaseImageUploader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import android.net.Uri
import androidx.core.net.toUri
import com.tlog.data.api.ProfileImageRequest
import com.tlog.data.model.share.toErrorMessage
import com.tlog.data.model.user.User
import com.tlog.ui.navigation.Screen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import retrofit2.HttpException
import kotlin.String

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
    private val tokenProvider: TokenProvider,
    private val userPreferences: UserPreferences
): ViewModel() {
    sealed interface UiEvent {
        data class Navigate(val target: Screen, val clearBackStack: Boolean = false): UiEvent
        data class ShowToast(val message: String): UiEvent
    }

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()


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
        viewModelScope.launch {
            try {
                _userInfo.value =  myPageRepository.getUserInfo().data

                _getUserInfo.value = true
            } catch (e: HttpException) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            } catch (e: Exception) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            }
        }
    }

    // 로그아웃
    fun logout() {
        val refreshToken = tokenProvider.getRefreshToken() ?: ""
        viewModelScope.launch {
            try {
                myPageRepository.logout(refreshToken)

                _uiEvent.trySend(UiEvent.ShowToast("로그아웃 성공"))
                _uiEvent.trySend(UiEvent.Navigate(Screen.Login, true))

                userPreferences.clearTokens()
            } catch (e: HttpException) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            } catch (e: Exception) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            }
        }
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
        viewModelScope.launch {
            try {
                val imageUrl = imageUpload(context, imageUri.value.toUri())

                myPageRepository.updateProfileImage(
                    ProfileImageRequest(
                        imageUrl = imageUrl
                    )
                )

                getUserInfo()

                _uiEvent.trySend(UiEvent.ShowToast("프로필 사진 변경 성공"))
            } catch (e: HttpException) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            } catch (e: Exception) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            }
        }
    }

    fun navToTbtiTest() {
        _uiEvent.trySend(UiEvent.Navigate(Screen.TbtiIntro))
    }
}

package com.tlog.viewmodel.share

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.local.UserPreferences
import com.tlog.data.repository.MyPageRepository
import com.tlog.data.util.FirebaseImageUploader
import com.tlog.viewmodel.share.MyPageViewModel.UiEvent.LogoutSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import kotlin.toString
import android.net.Uri
import android.widget.Toast
import androidx.core.net.toUri
import com.tlog.data.api.ProfileImageRequest
import com.tlog.data.model.share.toErrorMessage
import com.tlog.data.model.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.HttpException
import kotlin.String

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
    private val tokenProvider: TokenProvider,
    private val userPreferences: UserPreferences
): ViewModel() {

    private val _notification = mutableStateOf(true)
    val notification: State<Boolean> = _notification

    private val _userInfo: MutableState<User?> = mutableStateOf(null)
    val userInfo: State<User?> = _userInfo

    private val _image = mutableStateOf("")
    val imageUri = _image


    sealed class UiEvent {
        object LogoutSuccess: UiEvent()
        data class Error(val message: String): UiEvent()
        object ProfileImageUpdated: UiEvent()
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _getUserInfo = MutableStateFlow(false)
    val getUserInfo = _getUserInfo.asStateFlow()

    init {
        getUserInfo()
    }


    fun getUserInfo() {
        viewModelScope.launch {
            try {
                _userInfo.value =  myPageRepository.getUserInfo().data

                _getUserInfo.value = true
            } catch (e: HttpException) {
                _eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
            }
        }
    }

    // 로그아웃
    fun logout() {
        val refreshToken = tokenProvider.getRefreshToken() ?:""
        viewModelScope.launch {
            try {
                myPageRepository.logout(refreshToken)

                _eventFlow.emit(LogoutSuccess)
                userPreferences.clearTokens()

            } catch (e: HttpException) {
                _eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
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
                val response = myPageRepository.updateProfileImage(
                    ProfileImageRequest(
                        imageUrl = imageUrl
                    )
                )
                if (response.status == 200){
                    _eventFlow.emit(UiEvent.ProfileImageUpdated)
                    Toast.makeText(context, "프로필 사진 변경 성공", Toast.LENGTH_SHORT).show()
                }
            } catch (e: HttpException) {
                _eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
            }
        }
    }
}

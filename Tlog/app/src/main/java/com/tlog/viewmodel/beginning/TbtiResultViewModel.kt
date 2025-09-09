package com.tlog.viewmodel.beginning

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.LoginApi
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.api.FcmTokenBody
import com.tlog.data.api.RegisterRequest
import com.tlog.data.api.UserProfileDto
import com.tlog.data.local.UserPreferences
import com.tlog.data.model.share.TbtiDescription
import com.tlog.data.model.share.toErrorMessage
import com.tlog.data.repository.TbtiRepository
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException


@HiltViewModel
class TbtiResultViewModel @Inject constructor(
    private val tbtiRepository: TbtiRepository,
    private val loginApi: LoginApi,
    private val userPreferences: UserPreferences,
    private val tokenProvider: TokenProvider
): ViewModel() {
    sealed interface UiEvent {
        data class Navigate(val target: Screen, val clearBackStack: Boolean = false): UiEvent
        data class ShowToast(val message: String): UiEvent
    }

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()




    private val _tbtiDescription = mutableStateOf<TbtiDescription?>(null)
    val tbtiDescription: State<TbtiDescription?> = _tbtiDescription

    private var userId = ""

    init {
        userId = tokenProvider.getUserId() ?: ""
    }

    fun isUserId(): Boolean {
        return userId != ""
    }

    fun updateTbti(tbtiValue: String) {
        viewModelScope.launch {
            tbtiRepository.updateTbti(tbtiValue)
        }
        _uiEvent.trySend(UiEvent.ShowToast("TBTI 변경 성공"))
        _uiEvent.trySend(UiEvent.Navigate(Screen.MyPage, true))
    }

    fun fetchTbtiDescription(resultCode: String) {
        viewModelScope.launch {
            try {
                val response = tbtiRepository.getTbtiDescription(resultCode)
                _tbtiDescription.value = response.data
            } catch (e: HttpException) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            } catch (e: Exception) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            }
        }
    }

    fun registerUser(tbtiValue: String) {
        viewModelScope.launch {
            val socialAccessToken = userPreferences.getTmpSocialAccessToken()
            val socialType = userPreferences.getTmpSocialType()

            if (socialAccessToken.isNullOrEmpty()) { return@launch }

            val request = RegisterRequest(
                type = socialType.toString(),
                accessToken = socialAccessToken,
                userProfile = UserProfileDto(tbtiValue = tbtiValue)
            )

            try {
                val response = loginApi.ssoRegister(request)
                if (response.isSuccessful) {
                    val authorizationHeader = response.headers()["authorization"]
                    val setCookieHeader = response.headers()["set-cookie"]
                    if (authorizationHeader != null && setCookieHeader != null) {
                        userPreferences.saveTokensAndUserId(
                            authorizationHeader,
                            setCookieHeader,
                            response.body()!!.data.firebaseCustomToken
                        )
                        loginApi.setFcmToken(FcmTokenBody(userId = tokenProvider.getUserId()!!, firebaseToken = userPreferences.getFcmToken()!!))
                        _uiEvent.trySend(UiEvent.ShowToast("회원가입 성공"))
                        _uiEvent.trySend(UiEvent.Navigate(Screen.Main, true))
                    }
                } else {
                    _uiEvent.trySend(UiEvent.ShowToast("회원가입 실패"))
                }
            } catch (e: HttpException) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            } catch (e: Exception) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            }
        }
    }
}
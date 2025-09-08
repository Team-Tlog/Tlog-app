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
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException


@HiltViewModel
class TbtiResultViewModel @Inject constructor(
    private val tbtiRepository: TbtiRepository,
    private val loginApi: LoginApi,
    private val userPreferences: UserPreferences,
    private val tokenProvider: TokenProvider
): ViewModel() {

    sealed class UiEvent {
        object Success: UiEvent()
        data class Error(val message: String): UiEvent()
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()




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
        Log.d("hellosResultCode1", tbtiValue)
        viewModelScope.launch {
            tbtiRepository.updateTbti(tbtiValue)
        }
    }

    fun fetchTbtiDescription(resultCode: String) {
        viewModelScope.launch {
            try {
                val response = tbtiRepository.getTbtiDescription(resultCode)
                _tbtiDescription.value = response.data
            } catch (e: Exception) {
                Log.e("TbtiTestViewModel", "설명 데이터 요청 실패", e)
            }
        }
    }

    fun registerUser(tbtiValue: String) {
        viewModelScope.launch {
            val socialAccessToken = userPreferences.getTmpSocialAccessToken()
            val socialType = userPreferences.getTmpSocialType()

            if (socialAccessToken.isNullOrEmpty()) {
                return@launch
            }


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
                        _eventFlow.emit(UiEvent.Success)
                    }
                } else {
                    _eventFlow.emit(UiEvent.Error("회원가입 실패"))
                }
            } catch (e: HttpException) {
                _eventFlow.emit(UiEvent.Error("${e.toErrorMessage()}"))
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.Error("${e.toErrorMessage()}"))
            }
        }
    }
}
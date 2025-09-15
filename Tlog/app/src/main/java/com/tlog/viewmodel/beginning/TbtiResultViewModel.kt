package com.tlog.viewmodel.beginning

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.api.LoginApi
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.api.FcmTokenBody
import com.tlog.data.api.RegisterRequest
import com.tlog.data.api.UserProfileDto
import com.tlog.data.local.UserPreferences
import com.tlog.data.model.share.TbtiDescription
import com.tlog.data.repository.TbtiRepository
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch


@HiltViewModel
class TbtiResultViewModel @Inject constructor(
    private val tbtiRepository: TbtiRepository,
    private val loginApi: LoginApi,
    private val userPreferences: UserPreferences,
    private val tokenProvider: TokenProvider
): BaseViewModel() {




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
        launchSafeCall(
            action = {
                tbtiRepository.updateTbti(tbtiValue)
                showToast("TBTI 변경 성공")
                navigate(Screen.MyPage, true)
            }
        )
    }

    fun fetchTbtiDescription(resultCode: String) {
        launchSafeCall(
            action = {
                val response = tbtiRepository.getTbtiDescription(resultCode)
                _tbtiDescription.value = response.data
            }
        )
    }

    fun registerUser(tbtiValue: String) {
        launchSafeCall(
            action = {
                val socialAccessToken = userPreferences.getTmpSocialAccessToken()
                val socialType = userPreferences.getTmpSocialType()

                if (socialAccessToken.isNullOrEmpty()) { return@launchSafeCall }

                val request = RegisterRequest(
                    type = socialType.toString(),
                    accessToken = socialAccessToken,
                    userProfile = UserProfileDto(tbtiValue = tbtiValue)
                )

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
                        showToast("회원가입 성공")
                        navigate(Screen.Main, true)
                    }
                } else {
                    showToast("회원가입 실패")
                }
            }
        )
    }
}
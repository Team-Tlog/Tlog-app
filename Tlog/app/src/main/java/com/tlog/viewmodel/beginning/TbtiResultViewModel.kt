package com.tlog.viewmodel.beginning

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.tlog.api.LoginApi
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.api.FcmTokenBody
import com.tlog.data.api.RegisterRequest
import com.tlog.data.api.UserProfileDto
import com.tlog.data.local.UserPreferences
import com.tlog.data.model.share.TbtiDescription
import com.tlog.data.repository.TbtiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch


@HiltViewModel
class TbtiResultViewModel @Inject constructor(
    private val tbtiRepository: TbtiRepository,
    private val loginApi: LoginApi,
    private val userPreferences: UserPreferences,
    private val tokenProvider: TokenProvider
): ViewModel() {

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

    fun registerUser(
        navController: NavController,
        tbtiValue: String) {
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
                        navController.navigate("main") {
                            popUpTo(0)
                            launchSingleTop = true
                        }
                        Log.d("TbtiResultViewModel", "회원가입 성공 후 메인으로 이동")
                    }
                } else {
                    Log.e("TbtiResultViewModel", "회원가입 실패: ${response.body()?.status}")
                }
            } catch (e: Exception) {
                Log.e("TbtiResultViewModel", "회원가입 실패", e)
            }
        }
    }
}
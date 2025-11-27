package com.tlog.viewmodel.beginning

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.tlog.data.local.TokenProvider
import com.tlog.data.model.request.auth.FcmTokenRequest
import com.tlog.data.model.request.auth.RegisterRequest
import com.tlog.data.model.request.auth.UserProfile
import com.tlog.data.local.UserPreferences
import com.tlog.data.repository.ChooseMyTypeRepository
import com.tlog.ui.navigation.Screen
import com.tlog.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class ChooseMyTypeViewModel @Inject constructor(
    private val repository: ChooseMyTypeRepository,
    private val userPreferences: UserPreferences,
    private val tokenProvider: TokenProvider
) : BaseViewModel() {
    private val _selected = mutableStateOf(emptyList<Int>())
    val selected: State<List<Int>> = _selected

    fun toggleSelection(idx: Int) {
        _selected.value = if (_selected.value.contains(idx))
            _selected.value - idx
        else if (_selected.value.size < 3)
            _selected.value + idx
        else
            _selected.value
    }

    fun checkEnabled(): Boolean {
        return _selected.value.size in 1..3
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
                    userProfile = UserProfile(tbtiValue = tbtiValue),
                    preferTagIds = _selected.value
                )

                val response = repository.ssoRegister(request)

                if (response.isSuccessful) {
                    val authorizationHeader = response.headers()["authorization"]
                    val setCookieHeader = response.headers()["set-cookie"]
                    if (authorizationHeader != null && setCookieHeader != null) {
                        userPreferences.saveTokensAndUserId(
                            authorizationHeader,
                            setCookieHeader,
                            response.body()!!.data.firebaseCustomToken
                        )
                        repository.setFcmToken(FcmTokenRequest(userId = tokenProvider.getUserId()!!, firebaseToken = userPreferences.getFcmToken()!!))


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

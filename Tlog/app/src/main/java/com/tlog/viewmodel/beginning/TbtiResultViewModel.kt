package com.tlog.viewmodel.beginning

import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.data.local.TokenProvider
import com.tlog.data.model.share.TbtiDescription
import com.tlog.data.repository.TbtiRepository
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@HiltViewModel
class TbtiResultViewModel @Inject constructor(
    private val tbtiRepository: TbtiRepository,
    tokenProvider: TokenProvider
): BaseViewModel() {
    private val _tbtiDescription = MutableStateFlow<TbtiDescription?>(null)
    val tbtiDescription: StateFlow<TbtiDescription?> = _tbtiDescription

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

    fun navToSelectTravel(tbtiValue: String) {
        navigate(Screen.SelectTravel(tbtiValue))
    }
}

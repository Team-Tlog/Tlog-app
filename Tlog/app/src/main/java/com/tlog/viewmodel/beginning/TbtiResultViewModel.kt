package com.tlog.viewmodel.beginning

import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.data.local.TokenProvider
import com.tlog.data.repository.TbtiRepository
import com.tlog.domain.model.tbti.TbtiDescription
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
            },
            onSuccess = {
                showToast("TBTI 변경 성공")
                navigate(Screen.MyPage, true)
            }
        )
    }

    fun fetchTbtiDescription(resultCode: String) {
        launchSafeCall(
            action = {
                tbtiRepository.getTbtiDescription(resultCode)
            },
            onSuccess = {
                _tbtiDescription.value = it
            }
        )
    }

    fun navToSelectTravel(tbtiValue: String) {
        navigate(Screen.SelectTravel(tbtiValue))
    }
}

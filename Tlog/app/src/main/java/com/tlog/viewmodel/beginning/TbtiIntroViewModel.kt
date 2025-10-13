package com.tlog.viewmodel.beginning

import com.tlog.ui.navigation.Screen
import com.tlog.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject


@HiltViewModel
class TbtiIntroViewModel @Inject constructor(): BaseViewModel() {

    fun navToTbtiTest() {
        navigate(Screen.TbtiTest)
    }

    fun navToTbticodeInput() {
        navigate(Screen.TbtiCodeInput)
    }
}
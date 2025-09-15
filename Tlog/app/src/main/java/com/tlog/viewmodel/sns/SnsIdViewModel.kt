package com.tlog.viewmodel.sns

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.tlog.data.local.UserPreferences
import com.tlog.data.repository.SnsRepository
import com.tlog.ui.navigation.Screen
import com.tlog.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SnsIdViewModel @Inject constructor(
    private val repository: SnsRepository,
    private val userPreferences: UserPreferences
) : BaseViewModel() {

    // 입력된 ID
    private val _snsId = mutableStateOf("")
    val snsId: State<String> = _snsId

    // 중복 여부 (true: 중복됨, false: 사용 가능, null: 아직 입력 안함)
    private val _isDuplicated = mutableStateOf(false)
    val isDuplicated: State<Boolean?> = _isDuplicated


    fun updateSnsId(id: String) {
        launchSafeCall(
            action = {
                repository.updateSnsId(id)
                userPreferences.setSnsId(id)
                navigate(Screen.SnsMain, true)
            }
        )
    }


    fun updateId(newId: String) {
        _snsId.value = newId
        _isDuplicated.value = false
    }
}

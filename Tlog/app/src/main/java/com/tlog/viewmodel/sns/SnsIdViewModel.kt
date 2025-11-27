package com.tlog.viewmodel.sns

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.tlog.data.local.UserPreferences
import com.tlog.data.repository.SnsRepository
import com.tlog.ui.navigation.Screen
import com.tlog.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SnsIdViewModel @Inject constructor(
    private val repository: SnsRepository,
    private val userPreferences: UserPreferences
) : BaseViewModel() {
    private val _snsId = MutableStateFlow("")
    val snsId = _snsId.asStateFlow()

    private val _isDuplicated = MutableStateFlow(false)
    val isDuplicated = _isDuplicated.asStateFlow()

    fun updateSnsId(id: String) {
        launchSafeCall(
            action = {
                repository.updateSnsId(id)
                userPreferences.setSnsId(id)
                navigate(Screen.SnsMain, true)
            },
            onHttpError = { e ->
                if (e.code() == 409) {
                    _isDuplicated.value = true
                    true
                } else {
                    false
                }
            }
        )
    }

    fun updateId(newId: String) {
        _snsId.value = newId
        _isDuplicated.value = false
    }
}

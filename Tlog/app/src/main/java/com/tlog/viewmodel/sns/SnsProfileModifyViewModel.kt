package com.tlog.viewmodel.sns

import com.tlog.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SnsProfileModifyViewModel @Inject constructor() : BaseViewModel() {
    private val _profileName = MutableStateFlow("")
    val profileName = _profileName.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _introduce = MutableStateFlow("")
    val introduce = _introduce.asStateFlow()

    fun updateProfileName(value: String) {
        _profileName.value = value
    }

    fun updateName(value: String) {
        _name.value = value
    }

    fun updateIntroduce(value: String) {
        _introduce.value = value
    }
}
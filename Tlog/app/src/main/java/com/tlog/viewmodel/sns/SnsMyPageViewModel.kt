package com.tlog.viewmodel.sns

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.SnsUserProfile
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.repository.SnsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SnsMyPageViewModel @Inject constructor(
    private val repository: SnsRepository,
    tokenProvider: TokenProvider
): ViewModel() {
    private var userId: String? = ""

    init {
        userId = tokenProvider.getUserId()

        getUserProfile()
    }


    private var _userProfileInfo = MutableStateFlow<SnsUserProfile?>(null)
    val userProfileInfo: StateFlow<SnsUserProfile?> = _userProfileInfo





    fun getUserProfile() {
        viewModelScope.launch {
            try {
                val result = repository.getUserProfile(userId!!)

                when (result.status) {
                    200 -> {
                        _userProfileInfo.value = result.data
                    }
                    else -> {}
                }
            } catch (e: Exception) {
                Log.d("getUserProfile", e.toString())
            }
        }
    }
}
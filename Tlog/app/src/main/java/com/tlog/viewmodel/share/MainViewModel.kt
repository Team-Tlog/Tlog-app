package com.tlog.viewmodel.share

import androidx.lifecycle.ViewModel
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    tokenProvider: TokenProvider,
    private val mainRepository: MainRepository
): ViewModel() {
    var userId: String? = null
    init {
        userId = tokenProvider.getUserId()
    }


}
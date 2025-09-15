package com.tlog.viewmodel.share

import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    tokenProvider: TokenProvider,
    private val mainRepository: MainRepository
): BaseViewModel() {
    var userId: String? = null
    init {
        userId = tokenProvider.getUserId()
    }


}
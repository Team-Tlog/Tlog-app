package com.tlog.viewmodel.share

import android.util.Log
import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.data.local.TokenProvider
import com.tlog.data.model.travel.Scrap
import com.tlog.data.model.travel.Cart
import com.tlog.data.repository.ScrapAndCartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: ScrapAndCartRepository,
    tokenProvider: TokenProvider
): BaseViewModel() {
    var userId: String = ""

    private val _carts = MutableStateFlow<List<Cart>?>(null)
    val carts = _carts.asStateFlow()

    private val _scraps = MutableStateFlow<List<Scrap>?>(null)
    val scraps = _scraps.asStateFlow()


    init {
        userId = tokenProvider.getUserId() ?: ""
        fetchScrapList(userId)
        fetchCartList(userId)
    }

    fun fetchScrapList(userId: String) {
        launchSafeCall(
            action = {
                val result = repository.getUserScrap(userId)
                _scraps.value = result
            },
            onError = { Log.d("MapViewModel", it) }
        )
    }

    fun fetchCartList(userId: String) {
        launchSafeCall(
            action = {
                val result = repository.getUserCart(userId)
                _carts.value = result
            },
            onError = { Log.d("MapViewModel", it) }
        )
    }
}

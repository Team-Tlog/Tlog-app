package com.tlog.viewmodel.share

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.model.share.Location
import com.tlog.data.model.travel.Scrap
import com.tlog.data.model.travel.Cart
import com.tlog.data.repository.ScrapAndCartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: ScrapAndCartRepository,
    tokenProvider: TokenProvider
): BaseViewModel() {
    var userId: String = ""

    private var _cartList = mutableStateOf<List<Cart>?>(null)
    val cartList: State<List<Cart>?> = _cartList

    private var _scrapList = mutableStateOf<List<Scrap>?>(null)
    val scrapList: State<List<Scrap>?> = _scrapList

    private val _currentLocation = mutableStateOf<Location?>(null)
    val currentLocation: State<Location?> = _currentLocation

    init {
        userId = tokenProvider.getUserId() ?: ""
        fetchScrapList(userId)
        fetchCartList(userId)
    }

    fun fetchScrapList(userId: String) {
        launchSafeCall(
            action = {
                val result = repository.getUserScrap(userId)
                _scrapList.value = result
            },
            onError = { Log.d("MapViewModel", it) }
        )
    }

    fun fetchCartList(userId: String) {
        launchSafeCall(
            action = {
                val result = repository.getUserCart(userId)
                _cartList.value = result
            },
            onError = { Log.d("MapViewModel", it) }
        )
    }

    fun updateCurrentLocation(latitude: Double, longitude: Double) {
        _currentLocation.value = Location(latitude = latitude.toString(), longitude = longitude.toString())
    }

}
package com.tlog.viewmodel.share

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.api.ScrapDestinationResponse
import com.tlog.data.model.travel.ShopCart
import com.tlog.data.repository.ScrapAndCartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: ScrapAndCartRepository,
    tokenProvider: TokenProvider
): ViewModel() {
    var userId: String = ""

    private var _cartList = mutableStateOf<List<ShopCart>>(emptyList())
    val cartList: State<List<ShopCart>> = _cartList

    private var _scrapList = mutableStateOf<List<ScrapDestinationResponse>>(emptyList())
    val scrapList: State<List<ScrapDestinationResponse>> = _scrapList

    init {
        userId = tokenProvider.getUserId() ?: ""
        fetchScrapList(userId)
        fetchCartList(userId)
    }

    fun fetchScrapList(userId: String) {
        viewModelScope.launch {
            try {
                val result = repository.getUserScrap(userId)
                _scrapList.value = result
            } catch (e: Exception) {
                Log.d("MapViewModel", e.message.toString())
            }
        }
    }

    fun fetchCartList(userId: String) {
        viewModelScope.launch {
            try {
                val result = repository.getUserCart(userId)
                _cartList.value = result
            } catch (e: Exception) {
                Log.d("MapViewModel", e.message.toString())
            }
        }
    }

}
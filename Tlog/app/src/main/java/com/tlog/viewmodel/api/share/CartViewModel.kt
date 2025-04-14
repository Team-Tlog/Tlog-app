package com.tlog.viewmodel.api.share

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.data.repository.CartRepository
import com.tlog.ui.api.travel.TravelData
import kotlinx.coroutines.launch

class CartViewModel(
    private val userId: String
): ViewModel() {
    private val repository = CartRepository()

    init {
        fetchCart()
    }

    private var _cartList = mutableStateOf<List<TravelData>>(emptyList())
    val cartList: State<List<TravelData>> = _cartList

    fun fetchCart() {
        viewModelScope.launch {
            try {
                val result = repository.getUserCart(userId)
                _cartList.value = result ?: emptyList() // null이면 emptyList
            } catch (e: Exception) {
                // api실패 시
            }
        }
    }


    private var _checkedTravelList = mutableStateOf<List<String>>(emptyList())
    val checkedTravelList: State<List<String>> = _checkedTravelList

    fun updateCheckedTravelList(travelName: String) {
        if (_checkedTravelList.value.contains(travelName))
            _checkedTravelList.value -= travelName
        else
            _checkedTravelList.value += travelName
    }

    fun isChecked(travelName: String): Boolean {
        return _checkedTravelList.value.contains(travelName)
    }

    fun allChecked() {
        if (_checkedTravelList.value.size != _cartList.value.size)
            _checkedTravelList.value = _cartList.value.map { it.name }
        else
            _checkedTravelList.value = emptyList()
    }

}
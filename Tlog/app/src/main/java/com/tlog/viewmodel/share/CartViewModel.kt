package com.tlog.viewmodel.share

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tlog.data.model.travel.Travel
import com.tlog.data.repository.CartRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch


class CartViewModel @AssistedInject constructor(
    private val repository: CartRepository,
    @Assisted private val userId: String
): ViewModel() {

    init {
        fetchCart()
    }

    private fun fetchCart() {
        viewModelScope.launch {
            try {
                val result = repository.getUserCart(userId)
                _cartList.value = result ?: emptyList() // null이면 emptyList
            } catch (e: Exception) {
                // api실패 시
            }
        }
    }





    private var _cartList = mutableStateOf<List<Travel>>(emptyList())
    val cartList: State<List<Travel>> = _cartList

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


    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(userId: String): CartViewModel
    }

    companion object {
        fun provideFactory(
            factory: AssistedFactory,
            userId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return factory.create(userId) as T
            }
        }
    }
}



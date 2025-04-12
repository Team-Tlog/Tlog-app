package com.tlog.viewmodel.api.share

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.data.repository.CartRepository
import com.tlog.ui.api.travel.TravelData
import kotlinx.coroutines.launch

class CartViewModel: ViewModel() {
    private val repository = CartRepository()

    init {
        fetchCart()
    }

    private var _cartList = mutableStateOf<List<TravelData>>(emptyList())
    val cartList: State<List<TravelData>> = _cartList

    fun fetchCart() {
        viewModelScope.launch {
            try {
                val result = repository.getUserCart("94e94a78-170a-11f0-b854-02520f3d109f")
                _cartList.value = result ?: emptyList() // null이면 emptyList
            } catch (e: Exception) {
                _cartList.value = emptyList()
                Log.e("CartViewModel", "API 실패: ${e.message}")
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











    private var _allChecked = mutableStateOf(false)


    /*
    val api 보낼 때 사용할 친구 = travelList.value
    .filter { it.checked }
    .map { it.travelData }
     */

    fun updateTravelList(newTravelList: List<TravelData>) {
        _cartList.value = newTravelList
    }

//    fun updateChecked(index: Int, newChecked: Boolean) {
//        val updateList = _cartList.value.toMutableList()
//        val currentItem = updateList[index]
//
//        updateList[index] = currentItem.copy(checked = newChecked)
//        _cartList.value = updateList
//    }
//
//    fun allChecked() {
//        _allChecked.value = !(_allChecked.value)
//        _cartList.value = _cartList.value.map { // 리스트 원소 변환
//            it.copy(checked = _allChecked.value) // copy로 checked만 변경
//        }
//    }
//
//    fun getCheckedTravelList(): List<TravelUiData> {
//        return _cartList.value.filter { it.checked }
//    }
}
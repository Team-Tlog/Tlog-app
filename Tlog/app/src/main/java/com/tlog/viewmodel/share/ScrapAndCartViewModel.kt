package com.tlog.viewmodel.share

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.model.travel.Scrap
import com.tlog.data.model.travel.ShopCart
import com.tlog.data.repository.ScrapAndCartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ScrapAndCartViewModel @Inject constructor(
    private val repository: ScrapAndCartRepository,
    tokenProvider: TokenProvider
): ViewModel() {
    private var userId: String = ""

    init {
        userId = tokenProvider.getUserId()?: ""
        fetchScrapList()
    }

    fun fetchCart() {
        viewModelScope.launch {
            try {
                val result = repository.getUserCart(userId)
                _cartList.value = result
            } catch (e: Exception) {
                // api실패 시
            }
        }
    }

    private var _selectedTab = mutableStateOf("스크랩")
    val selectedTab: State<String> = _selectedTab

    fun updateSelectedTab(tab: String) {
        _selectedTab.value = tab
    }

    private var _cartList = mutableStateOf<List<ShopCart>>(emptyList())
    val cartList: State<List<ShopCart>> = _cartList

    private var _scrapList = mutableStateOf<List<Scrap>>(emptyList())
    val scrapList: State<List<Scrap>> = _scrapList

    fun fetchScrapList() {
        viewModelScope.launch {
            try {
                val result = repository.getUserScrap(userId)
                _scrapList.value = result
            } catch (e: Exception) {
                Log.d("ScrapAndCartViewModel", e.message.toString())
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

    fun clearChecked() {
        _checkedTravelList.value = emptyList()
    }

    fun deleteSelectedItems(selectedTab: String) {
        viewModelScope.launch {
            try {
                checkedTravelList.value.forEach { destName ->
                    if (selectedTab == "스크랩") {
                        val destinationId = scrapList.value.find { it.name == destName }?.id ?: return@forEach
                        repository.deleteScrapDestination(userId, destinationId)
                    } else {
                        val destinationId = cartList.value.find { it.name == destName }?.id ?: return@forEach
                        repository.deleteTravelFromCart(userId, destinationId)
                    }
                }
                clearChecked()
                if (selectedTab == "스크랩") {
                    fetchScrapList()
                } else {
                    fetchCart()
                }
            } catch (e: Exception) {
                Log.d("ScrapAndCartViewModel", e.message.toString())

            }
        }
    }


    fun addSelectedTravelToCart() {
        viewModelScope.launch {
            try {
                checkedTravelList.value.forEach { destName ->
                    val destinationId = scrapList.value.find { it.name == destName }?.id ?: return@forEach
                    repository.addDestinationToCart(userId, destinationId)
                }
                clearChecked()
                fetchCart()
            } catch (e: Exception) {
                Log.d("ScrapAndCartViewModel", e.message.toString())
            }
        }
    }


    fun allChecked(selectedTab: String) {
        val allItems = if (selectedTab == "스크랩") {
            scrapList.value.map { it.name }
        } else {
            cartList.value.map { it.name }
        }
        if (_checkedTravelList.value.size != allItems.size)
            _checkedTravelList.value = allItems
        else
            _checkedTravelList.value = emptyList()
    }


}



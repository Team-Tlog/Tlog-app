package com.tlog.viewmodel.share

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.model.travel.Scrap
import com.tlog.data.model.travel.Cart
import com.tlog.data.repository.ScrapAndCartRepository
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ScrapAndCartViewModel @Inject constructor(
    private val repository: ScrapAndCartRepository,
    tokenProvider: TokenProvider
): BaseViewModel() {



    private var userId: String = ""

    private var _cartList = mutableStateOf<List<Cart>>(emptyList())
    val cartList: State<List<Cart>> = _cartList

    private var _scrapList = mutableStateOf<List<Scrap>>(emptyList())
    val scrapList: State<List<Scrap>> = _scrapList

    init {
        userId = tokenProvider.getUserId()?: ""

        fetchScrapList()
    }

    fun fetchCart() {
        launchSafeCall(
            action = {
                _cartList.value = repository.getUserCart(userId)
            }
        )
    }

    private var _selectedTab = mutableStateOf("스크랩")
    val selectedTab: State<String> = _selectedTab

    fun updateSelectedTab(tab: String) {
        _selectedTab.value = tab
    }



    fun fetchScrapList() {
        launchSafeCall(
            action = {
                _scrapList.value = repository.getUserScrap(userId)
            }
        )
    }



    fun deleteSelectedItems(selectedTab: String) {
        launchSafeCall(
            action = {
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
            }
        )
    }


    fun addSelectedTravelToCart() {
        launchSafeCall(
            action = {
                checkedTravelList.value.forEach { destName ->
                    val destinationId = scrapList.value.find { it.name == destName }?.id ?: return@forEach
                    repository.addDestinationToCart(userId, destinationId)
                }
                clearChecked()
                fetchCart()
            }
        )
    }




    // checkBox
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

    fun navToTravelInfo(travelId: String) {
        navigate(Screen.TravelInfo(travelId))
    }
}



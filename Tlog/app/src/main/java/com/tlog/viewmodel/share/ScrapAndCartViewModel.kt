package com.tlog.viewmodel.share

import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.data.local.TokenProvider
import com.tlog.data.dto.travel.Scrap
import com.tlog.data.dto.travel.Cart
import com.tlog.data.repository.ScrapAndCartRepository
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ScrapAndCartViewModel @Inject constructor(
    private val repository: ScrapAndCartRepository,
    tokenProvider: TokenProvider
): BaseViewModel() {
    private var userId: String = ""

    private val _carts = MutableStateFlow<List<Cart>>(emptyList())
    val carts = _carts.asStateFlow()

    private val _scraps = MutableStateFlow<List<Scrap>>(emptyList())
    val scraps = _scraps.asStateFlow()

    private val _selectedTab = MutableStateFlow("스크랩")
    val selectedTab = _selectedTab.asStateFlow()

    private val _checkedTravelList = MutableStateFlow<List<String>>(emptyList())
    val checkedTravelList = _checkedTravelList.asStateFlow()


    init {
        userId = tokenProvider.getUserId()?: ""

        fetchScrapList()
    }

    fun fetchCart() {
        launchSafeCall(
            action = {
                _carts.value = repository.getUserCart(userId)
            }
        )
    }

    fun updateSelectedTab(tab: String) {
        _selectedTab.value = tab
    }

    fun fetchScrapList() {
        launchSafeCall(
            action = {
                _scraps.value = repository.getUserScrap(userId)
            }
        )
    }

    fun deleteSelectedItems(selectedTab: String) {
        launchSafeCall(
            action = {
                checkedTravelList.value.forEach { destName ->
                    if (selectedTab == "스크랩") {
                        val destinationId = scraps.value.find { it.name == destName }?.id ?: return@forEach
                        repository.deleteScrapDestination(userId, destinationId)
                    } else {
                        val destinationId = carts.value.find { it.name == destName }?.id ?: return@forEach
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
                    val destinationId = scraps.value.find { it.name == destName }?.id ?: return@forEach
                    repository.addDestinationToCart(userId, destinationId)
                }
                clearChecked()
                fetchCart()
            }
        )
    }

    fun updateCheckedTravelList(travelName: String) {
        val current = _checkedTravelList.value

        _checkedTravelList.value =
            if (current.contains(travelName))
                current - travelName
            else
                current + travelName
    }

    fun clearChecked() {
        _checkedTravelList.value = emptyList()
    }

    fun allChecked(selectedTab: String) {
        val allItems = if (selectedTab == "스크랩") {
            scraps.value.map { it.name }
        } else {
            carts.value.map { it.name }
        }
        if (_checkedTravelList.value.size != allItems.size)
            _checkedTravelList.value = allItems
        else
            _checkedTravelList.value = emptyList()
    }

    fun navToTravelInfo(travelId: String) {
        navigate(Screen.TravelInfo(travelId))
    }

    fun navToAiCourse() {
        navigate(Screen.AiCourseInput())
    }
}

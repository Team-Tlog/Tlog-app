package com.tlog.viewmodel.share

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.tlog.data.model.restaurant.Restaurant
import com.tlog.data.repository.RestaurantRepository
import com.tlog.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val repository: RestaurantRepository
): BaseViewModel() {
    private var _selectedTab = mutableStateOf("식당")
    val selectedTab: State<String> = _selectedTab

    private var _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val restaurants: StateFlow<List<Restaurant>> = _restaurants.asStateFlow()

    private var _cafes = MutableStateFlow<List<Restaurant>>(emptyList())
    val cafes: StateFlow<List<Restaurant>> = _cafes.asStateFlow()

    fun updateSelectedTab(tab: String) {
        _selectedTab.value = tab
    }

    fun getRestaurants(latitude: Double, longitude: Double) {
        launchSafeCall(
            action = {
                val response = repository.getEateryList(latitude, longitude)

                _restaurants.value = response.data
            }
        )
    }

    fun getCafes(latitude: Double, longitude: Double) {
        launchSafeCall(
            action = {
                val response = repository.getCafeList(latitude, longitude)

                _cafes.value = response.data
            }
        )
    }
}
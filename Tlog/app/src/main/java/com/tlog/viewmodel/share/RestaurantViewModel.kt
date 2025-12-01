package com.tlog.viewmodel.share

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.tlog.data.model.response.restaurant.Restaurant
import com.tlog.data.repository.RestaurantRepository
import com.tlog.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val repository: RestaurantRepository
): BaseViewModel() {
    private var _selectedTab = MutableStateFlow("식당")
    val selectedTab = _selectedTab.asStateFlow()

    private var _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val restaurants = _restaurants.asStateFlow()

    private var _cafes = MutableStateFlow<List<Restaurant>>(emptyList())
    val cafes = _cafes.asStateFlow()

    fun updateSelectedTab(tab: String) {
        _selectedTab.value = tab
    }

    private var _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun getRestaurants(latitude: Double, longitude: Double) {
        launchSafeCall(
            action = {
                val restaurantResponse = repository.getEateryList(latitude, longitude)

                _restaurants.value = restaurantResponse.data

                val cafeResponse = repository.getCafeList(latitude, longitude)

                _cafes.value = cafeResponse.data

                _isLoading.value = true
            }
        )
    }
}

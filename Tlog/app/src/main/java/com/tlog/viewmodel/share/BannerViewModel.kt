package com.tlog.viewmodel.share

import androidx.compose.runtime.State
import com.tlog.data.local.TokenProvider
import com.tlog.data.model.response.travel.TravelDestination
import com.tlog.data.local.ScrapManager
import com.tlog.data.repository.BannerRepository
import com.tlog.ui.navigation.Screen
import com.tlog.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


@HiltViewModel
class BannerViewModel @Inject constructor(
    private val repository: BannerRepository,
    private val scrapManager: ScrapManager,
    tokenProvider: TokenProvider
): BaseViewModel() {
    private var userId: String? = null
    private val _scraps = MutableStateFlow<List<String>>(emptyList())
    val scraps = _scraps.asStateFlow()

    private val _destinations = MutableStateFlow<List<TravelDestination>>(emptyList())
    val destinations: StateFlow<List<TravelDestination>> = _destinations.asStateFlow()

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    init {
        userId = tokenProvider.getUserId()
    }

    fun getBannerDetail(bannerId: String) {
        launchSafeCall(
            action = {
                val response = repository.getBannerDetail(bannerId)

                _destinations.value = response.data.destinations.content
                _title.value = response.data.title
            }
        )
    }

    fun toggleScrap(destinationId: String) {
        launchSafeCall(
            action = {
                scrapManager.toggleScrap(destinationId)

                _scraps.value = scrapManager.scrapList.value
            }
        )
    }

    fun navToTravelInfo(travelId: String) {
        navigate(Screen.TravelInfo(travelId))
    }
}

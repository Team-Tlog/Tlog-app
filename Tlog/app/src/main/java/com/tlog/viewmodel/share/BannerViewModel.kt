package com.tlog.viewmodel.share

import androidx.compose.runtime.State
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.api.TravelDestinationResponse
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
    tokenProvider: TokenProvider,
    private var userId: String? = null
): BaseViewModel() {
    init {
        userId = tokenProvider.getUserId()
    }
    val scrapList: State<List<String>> = scrapManager.scrapList

    private val _destinations = MutableStateFlow<List<TravelDestinationResponse>>(emptyList())
    val destinations: StateFlow<List<TravelDestinationResponse>> = _destinations.asStateFlow()

    fun getBannerDetail(bannerId: String) {
        launchSafeCall(
            action = {
                val response = repository.getBannerDetail(bannerId)

                _destinations.value = response.data.content
            }
        )
    }

    fun toggleScrap(destinationId: String) {
        launchSafeCall(
            action = {
                scrapManager.toggleScrap(destinationId)
            }
        )
    }

    fun navToTravelInfo(travelId: String) {
        navigate(Screen.TravelInfo(travelId))
    }
}
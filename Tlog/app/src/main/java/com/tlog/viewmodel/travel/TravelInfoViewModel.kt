package com.tlog.viewmodel.travel

import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.api.TravelDetailResponse
import com.tlog.data.local.ScrapManager
import com.tlog.data.repository.SearchOneDestinationRepository
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class TravelInfoViewModel @Inject constructor(
    private val repository: SearchOneDestinationRepository,
    private val scrapManager: ScrapManager,
    tokenProvider: TokenProvider
) : BaseViewModel() {

    private var userId: String? = null

    init {
        userId = tokenProvider.getUserId()
    }


    private val _destinationDetail = MutableStateFlow<TravelDetailResponse?>(null)
    val destinationDetail: StateFlow<TravelDetailResponse?> = _destinationDetail

    private val _sortOption = MutableStateFlow("추천순")
    val sortOption: StateFlow<String> = _sortOption

    fun updateSelectOption(newOption: String) {
        _sortOption.value = newOption
    }

    fun getTravelInfo(id: String) {
        launchSafeCall(
            action = {
                val response = repository.getDestinationById(id)
                _destinationDetail.value = response.data
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

    fun isScraped(destinationId: String): Boolean {
        return scrapManager.isScraped(destinationId)
    }

    fun navToTravelInfo(travelId: String) {
        navigate(Screen.TravelInfo(travelId))
    }

    fun navToSnsMyPage(userId: String) {
        navigate(Screen.SnsMyPage(userId))
    }

    fun navToReviewWrite(travelId: String, destinationName: String) {
        navigate(Screen.ReviewWrite(travelId, destinationName))
    }

    fun navToReviewList(travelId: String, destinationName: String) {
        navigate(Screen.ReviewList(travelId, destinationName))
    }
}
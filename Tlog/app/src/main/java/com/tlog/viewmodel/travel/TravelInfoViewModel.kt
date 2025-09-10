package com.tlog.viewmodel.travel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.api.TravelDetailResponse
import com.tlog.data.local.ScrapManager
import com.tlog.data.model.share.toErrorMessage
import com.tlog.data.repository.SearchOneDestinationRepository
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

@HiltViewModel
class TravelInfoViewModel @Inject constructor(
    private val repository: SearchOneDestinationRepository,
    private val scrapManager: ScrapManager,
    tokenProvider: TokenProvider
) : ViewModel() {
    sealed interface UiEvent {
        data class Navigate(val target: Screen, val clearBackStack: Boolean = false): UiEvent
        data class ShowToast(val message: String) : UiEvent
    }

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

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
        viewModelScope.launch {
            try {
                val response = repository.getDestinationById(id)
                _destinationDetail.value = response.data
            } catch (e: HttpException) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            } catch (e: Exception) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            }
        }
    }

    fun toggleScrap(destinationId: String) {
        viewModelScope.launch {
            try {
                scrapManager.toggleScrap(destinationId)
            } catch (e: HttpException) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            } catch (e: Exception) {
                _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
            }
        }
    }

    fun isScraped(destinationId: String): Boolean {
        return scrapManager.isScraped(destinationId)
    }

    fun navToTravelInfo(travelId: String) {
        _uiEvent.trySend(UiEvent.Navigate(Screen.TravelInfo(travelId)))
    }

    fun navToSnsMyPage(userId: String) {
        _uiEvent.trySend(UiEvent.Navigate(Screen.SnsMyPage(userId)))
    }

    fun navToReviewWrite(travelId: String, destinationName: String) {
        _uiEvent.trySend(UiEvent.Navigate(Screen.ReviewWrite(travelId, destinationName)))
    }

    fun navToReviewList(travelId: String, destinationName: String) {
        _uiEvent.trySend(UiEvent.Navigate(Screen.ReviewList(travelId, destinationName)))
    }
}
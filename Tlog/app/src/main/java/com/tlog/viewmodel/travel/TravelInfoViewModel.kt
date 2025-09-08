package com.tlog.viewmodel.travel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.api.TravelDetailResponse
import com.tlog.data.local.ScrapManager
import com.tlog.data.model.share.toErrorMessage
import com.tlog.data.repository.SearchOneDestinationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

@HiltViewModel
class TravelInfoViewModel @Inject constructor(
    private val repository: SearchOneDestinationRepository,
    private val scrapManager: ScrapManager,
    tokenProvider: TokenProvider
) : ViewModel() {
    sealed class UiEvent {
        data class Error(val message: String) : UiEvent()
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
                _eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
            }
        }
    }

    fun toggleScrap(destinationId: String) {
        viewModelScope.launch {
            try {
                scrapManager.toggleScrap(destinationId)
            } catch (e: HttpException) {
                _eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
            }
        }
    }

    fun isScraped(destinationId: String): Boolean {
        return scrapManager.isScraped(destinationId)
    }
}
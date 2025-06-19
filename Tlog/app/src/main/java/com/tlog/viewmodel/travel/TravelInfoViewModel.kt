package com.tlog.viewmodel.travel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.TravelApi
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.api.TravelDetailResponse
import com.tlog.data.local.ScrapManager
import com.tlog.data.repository.SearchOneDestinationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TravelInfoViewModel @Inject constructor(
    private val repository: SearchOneDestinationRepository,
    private val scrapManager: ScrapManager,
    tokenProvider: TokenProvider
) : ViewModel() {
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

    fun loadDestinationById(id: String) {
        viewModelScope.launch {
            try {
                val response = repository.getDestinationById(id)
                Log.d("okhttp", response.data.toString())
                _destinationDetail.value = response.data
            }
            catch (e: Exception) {
                Log.d("okhttp", e.message.toString())
            }
        }
    }

    fun toggleScrap(destinationId: String) {
        viewModelScope.launch {
            try {
                scrapManager.toggleScrap(destinationId)
            } catch (e: Exception) {
                // TODO: Error handling
            }
        }
    }

    fun isScraped(destinationId: String): Boolean {
        return scrapManager.isScraped(destinationId)
    }
}
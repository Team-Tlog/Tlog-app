package com.tlog.viewmodel.travel

import android.util.Log
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.api.TravelDestinationResponse
import com.tlog.data.local.ScrapManager
import com.tlog.data.repository.TravelListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TravelListViewModel @Inject constructor(
    private val repository: TravelListRepository,
    private val scrapManager: ScrapManager,
    tokenProvider: TokenProvider
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow("추천순")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _destinations = MutableStateFlow<List<TravelDestinationResponse>>(emptyList())
    val destinations: StateFlow<List<TravelDestinationResponse>> = _destinations.asStateFlow()

    val scrapList: State<List<String>> = scrapManager.scrapList

    private var userId: String? = null

    init {
        userId = tokenProvider.getUserId()
    }


    fun initUserIdAndScrapList() {
        viewModelScope.launch {
            userId?.let { scrapManager.refreshScrapList(it) }
            scrapManager.init()
        }
    }

    fun setCategory(category: String) {
        _selectedCategory.value = category

        val sortedList = when (category) {
            "추천순" -> _destinations.value
            "인기순" -> _destinations.value.sortedByDescending { it.averageRating }
            "리뷰순" -> _destinations.value.sortedByDescending { it.reviewCount }
            else -> _destinations.value
        }

        _destinations.value = sortedList
    }


    fun toggleScrap(destinationId: String) {
        viewModelScope.launch {
            try {
                scrapManager.toggleScrap(destinationId)
            } catch (e: Exception) {
                Log.d("scrap", "스크랩 에러: ${e.message}")
            }
        }
    }


    private var page = 0
    private val pageSize = 10
    private val sort = emptyList<String>()
    private var isLastPage = false



    fun getTravelList(
        city: String,
        sortType: String? = "REVIEW", // 현재는 리뷰 추후 변경 할 것 (api가 리뷰만 돌아감)
        tbti: String? = null
    ) {
        viewModelScope.launch {
            try {
                val response = repository.getTravelList(
                    page = page,
                    size = pageSize,
                    sort = sort,
                    city = city,
                    sortType = sortType,
                    tbti = tbti
                )
                isLastPage = response.data.last
                _destinations.value = response.data.content
            } catch (e: Exception) {
                Log.d("TravelDestinationRecommendationViewModel", "에러 로그 : ${e.message}")
            }
        }
    }

    fun getNextPage(
        city: String,
        sortType: String? = "REVIEW",
        tbti: String? = null
    ) {
        if (isLastPage == true) return
        page++
        viewModelScope.launch {
            try {
                val response = repository.getTravelList(
                    page = page,
                    size = pageSize,
                    sort = sort,
                    city = city,
                    sortType = sortType,
                    tbti = tbti
                )
                isLastPage = response.data.last
                _destinations.value += response.data.content
            } catch (e: Exception) {
                Log.d("TravelDestinationRecommendationViewModel", "에러 로그 : ${e.message}")
            }
        }
    }



    fun searchTravelToCity(city: String) {
        viewModelScope.launch {
            try {
                val response = repository.getSearchToCity(page = page, size = pageSize, sort = sort, query = city)
                _destinations.value = response.data.content
                isLastPage = response.data.last
            } catch (e: Exception) {
                Log.d("TravelDestinationRecommendationViewModel", "에러 로그 : ${e.message}")
                _destinations.value = emptyList()
            }
        }
    }
}

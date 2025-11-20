package com.tlog.viewmodel.travel

import androidx.compose.runtime.State
import com.tlog.api.retrofit.TokenProvider
import com.tlog.data.api.TravelDestinationResponse
import com.tlog.data.local.RegionCode
import com.tlog.data.local.ScrapManager
import com.tlog.data.repository.TravelListRepository
import com.tlog.ui.navigation.Screen
import com.tlog.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class TravelListViewModel @Inject constructor(
    private val repository: TravelListRepository,
    private val scrapManager: ScrapManager,
    tokenProvider: TokenProvider
) : BaseViewModel() {

    private val _selectedCategory = MutableStateFlow("추천순")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _destinations = MutableStateFlow<List<TravelDestinationResponse>>(emptyList())
    val destinations: StateFlow<List<TravelDestinationResponse>> = _destinations.asStateFlow()

    val scrapList: State<List<String>> = scrapManager.scrapList

    private var userId: String? = null
    private var currentCity: String? = null
    private var currentSortType: String? = "RECOMMEND"


    init {
        userId = tokenProvider.getUserId()
    }


    fun initUserIdAndScrapList() {
        launchSafeCall(
            action = {
                userId?.let { scrapManager.refreshScrapList(it) }
                scrapManager.init()
            }
        )
    }

    fun setCategory(category: String) {
        _selectedCategory.value = category

        currentCity?.let { city ->
            page = 0
            isLastPage = false

            when (category) {
                "추천순" -> {
                    // RECOMMEND 정렬은 지역코드를 사용
                    val cityCode = RegionCode.fromStringOrNull(city)
                    getTravelListInternal(city = cityCode.toString(), sortType = "RECOMMEND")
                }
                "인기순" -> {
                    // POPULAR 정렬은 원래 문자열 사용
                    getTravelListInternal(city = city, sortType = "POPULAR")
                }
                "리뷰순" -> {
                    // REVIEW 정렬은 원래 문자열 사용
                    getTravelListInternal(city = city, sortType = "REVIEW")
                }
            }
        }
    }


    fun toggleScrap(destinationId: String) {
        launchSafeCall(
            action = {
                scrapManager.toggleScrap(destinationId)
            }
        )
    }



    private var page = 0
    private val pageSize = 10
    private val sort = emptyList<String>()
    private var isLastPage = false

    fun getTravelList(
        city: String,
        sortType: String? = "RECOMMEND"
    ) {
        currentCity = city // 현재 city 문자열 저장
        currentSortType = sortType // 현재 sortType 저장

        // RECOMMEND일 때만 지역코드로 변환, 나머지는 문자열 그대로 사용
        val actualCity = if (sortType == "RECOMMEND") {
            RegionCode.fromStringOrNull(city).toString()
        } else {
            city
        }

        launchSafeCall(
            action = {
                val response = repository.getTravelList(
                    page = page,
                    size = pageSize,
                    sort = sort,
                    city = actualCity,
                    sortType = sortType
                )
                isLastPage = response.data.last
                _destinations.value = response.data.content
            }
        )
    }

    private fun getTravelListInternal(
        city: String,
        sortType: String? = "RECOMMEND"
    ) {
        currentSortType = sortType // 현재 sortType 저장

        launchSafeCall(
            action = {
                val response = repository.getTravelList(
                    page = page,
                    size = pageSize,
                    sort = sort,
                    city = city,
                    sortType = sortType
                )
                isLastPage = response.data.last
                _destinations.value = response.data.content
            }
        )
    }

    fun getNextPage() {
        if (isLastPage) return
        page++

        val city = currentCity ?: return
        val sortType = currentSortType

        // RECOMMEND일 때만 지역코드로 변환, 나머지는 문자열 그대로 사용
        val actualCity = if (sortType == "RECOMMEND") {
            RegionCode.fromStringOrNull(city).toString()
        } else {
            city
        }

        launchSafeCall(
            action = {
                val response = repository.getTravelList(
                    page = page,
                    size = pageSize,
                    sort = sort,
                    city = actualCity,
                    sortType = sortType
                )
                isLastPage = response.data.last
                _destinations.value += response.data.content
            }
        )
    }

    fun searchTravelToCity(city: String) {
        launchSafeCall(
            action = {
                val response = repository.getSearchToCity(page = page, size = pageSize, sort = sort, query = city)
                _destinations.value = response.data.content
                isLastPage = response.data.last
            }
        )
    }


    fun navToTravelInfo(travelId: String) {
        navigate(Screen.TravelInfo(travelId))
    }
}

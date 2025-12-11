package com.tlog.viewmodel.share

import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.data.local.RecentSearchPreferences
import com.tlog.data.repository.SearchRepository
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import androidx.lifecycle.viewModelScope
import com.tlog.domain.model.travel.PopularTravel
import com.tlog.domain.model.travel.ViewTravel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository,
    private val recentSearchPreferences: RecentSearchPreferences
): BaseViewModel() {
    private val _searchResult = MutableStateFlow<List<ViewTravel>>(emptyList())
    val searchResult = _searchResult.asStateFlow()

    // 인기 여행지
    private val _popularDestinations = MutableStateFlow<List<PopularTravel>>(emptyList())
    val popularDestinations = _popularDestinations.asStateFlow()

    // 최근 검색어 (최대 5개)
    private val _recentSearches = MutableStateFlow<List<String>>(emptyList())
    val recentSearches = _recentSearches.asStateFlow()

    // 검색어
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    init {
        // 저장된 최근 검색어 로드
        loadRecentSearches()

        viewModelScope.launch {
            @OptIn(FlowPreview::class) // debounce 때문에 사용
            _searchText
                .debounce(500) // 입력 없을 때
                .filter { it.isNotBlank() && it.isNotEmpty()} // 공백 무시 / 길이 1이상
                .distinctUntilChanged()
                .collect {
                    try {
                        searchTravel(it)
                    } catch (e: Exception) {
                        showToast(e.message ?: "검색 중 오류가 발생했습니다")
                    }

                }
        }

        // 인기 여행지 로드
        loadPopularDestinations()
    }

    // 저장된 최근 검색어 로드
    private fun loadRecentSearches() {
        viewModelScope.launch {
            try {
                _recentSearches.value = recentSearchPreferences.getRecentSearches()
            } catch (e: Exception) {
                // 오류 발생 시 빈 리스트 유지
                _recentSearches.value = emptyList()
            }
        }
    }

    suspend fun searchTravel(searchText: String) {
        _searchResult.value = repository.searchTravel(searchText)
    }

    fun updateSearchText(newSearchText: String) {
        _searchText.value = newSearchText
        if (newSearchText.isBlank()) {
            _searchResult.value = emptyList()
        }
    }

    fun checkSearchText(): Boolean {
        return searchText.value.isNotBlank()
    }

    private fun loadPopularDestinations() {
        launchSafeCall(
            action = {
                repository.getPopularDestinations()
            },
            onSuccess = {
                _popularDestinations.value = it
            }
        )
    }

    // 최근 검색어 추가 (최대 5개, 중복 제거, 최신순)
    private fun addRecentSearch(searchText: String) {
        viewModelScope.launch {
            val currentList = _recentSearches.value.toMutableList()

            // 이미 존재하면 제거 (최신 순으로 다시 추가하기 위해)
            currentList.remove(searchText)

            // 맨 앞에 추가
            currentList.add(0, searchText)

            // 최대 5개까지만 유지
            val newList = currentList.take(5)
            _recentSearches.value = newList

            // 로컬에 저장
            try {
                recentSearchPreferences.saveRecentSearches(newList)
            } catch (e: Exception) {
                // 저장 실패 시 무시
            }
        }
    }

    // 최근 검색어 삭제
    fun removeRecentSearch(searchText: String) {
        viewModelScope.launch {
            val newList = _recentSearches.value.filter { it != searchText }
            _recentSearches.value = newList

            // 로컬에 저장
            try {
                recentSearchPreferences.saveRecentSearches(newList)
            } catch (e: Exception) {
                // 저장 실패 시 무시
            }
        }
    }

    // 최근 검색어 전체 삭제
    fun clearRecentSearches() {
        viewModelScope.launch {
            _recentSearches.value = emptyList()

            // 로컬에서 삭제
            try {
                recentSearchPreferences.clearRecentSearches()
            } catch (e: Exception) {
                // 삭제 실패 시 무시
            }
        }
    }

    // 최근 검색어 클릭 시 검색
    fun onRecentSearchClick(searchText: String) {
        _searchText.value = searchText
    }

    // Nav
    fun navToReviewWrite(travelId: String, travelName: String) {
        navigate(Screen.ReviewWrite(travelId, travelName))
    }

    fun navToTravelInfo(travelId: String) {
        // 현재 검색어가 있고 검색 결과가 있으면 최근 검색어에 추가
        if (searchText.value.isNotBlank() && searchResult.value.isNotEmpty()) {
            addRecentSearch(searchText.value)
        }
        navigate(Screen.TravelInfo(travelId))
    }

    fun navToAddTravel() {
        navigate(Screen.AddTravel)
    }
}

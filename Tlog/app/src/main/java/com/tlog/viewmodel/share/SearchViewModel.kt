package com.tlog.viewmodel.share

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.data.api.SearchTravel
import com.tlog.data.repository.SearchRepository
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
): BaseViewModel() {

    private var _searchResult = mutableStateOf<List<SearchTravel>>(emptyList())
    val searchResult: State<List<SearchTravel>> = _searchResult

    // 검색어
    private var _searchText = MutableStateFlow("")
    val searchText = _searchText

    init {
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
    }


    suspend fun searchTravel(searchText: String) {
        val response = repository.searchTravel(searchText)
        _searchResult.value = response.data
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



    // Nav
    fun navToReviewWrite(travelId: String, travelName: String) {
        navigate(Screen.ReviewWrite(travelId, travelName))
    }

    fun navToTravelInfo(travelId: String) {
        navigate(Screen.TravelInfo(travelId))
    }

    fun navToAddTravel() {
        navigate(Screen.AddTravel)
    }
}
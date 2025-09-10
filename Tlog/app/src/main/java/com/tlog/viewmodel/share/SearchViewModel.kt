package com.tlog.viewmodel.share

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.data.api.SearchTravel
import com.tlog.data.model.share.toErrorMessage
import com.tlog.data.repository.SearchRepository
import com.tlog.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
): ViewModel() {
    sealed interface UiEvent {
        data class Navigate(val target: Screen, val clearBackStack: Boolean = false): UiEvent
        data class ShowToast(val message: String): UiEvent
    }

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

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
                    } catch (e: HttpException) {
                        _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
                    } catch (e: Exception) {
                        _uiEvent.trySend(UiEvent.ShowToast(e.toErrorMessage()))
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
        _uiEvent.trySend(UiEvent.Navigate(Screen.ReviewWrite(travelId, travelName)))
    }

    fun navToTravelInfo(travelId: String) {
        _uiEvent.trySend(UiEvent.Navigate(Screen.TravelInfo(travelId)))
    }

    fun navToAddTravel() {
        _uiEvent.trySend(UiEvent.Navigate(Screen.AddTravel))
    }
}
package com.tlog.viewmodel.share

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.data.api.SearchTravel
import com.tlog.data.model.share.toErrorMessage
import com.tlog.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
): ViewModel() {
    sealed class UiEvent {
        data class Error(val message: String): UiEvent()
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
                        _eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
                    } catch (e: Exception) {
                        _eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
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
}
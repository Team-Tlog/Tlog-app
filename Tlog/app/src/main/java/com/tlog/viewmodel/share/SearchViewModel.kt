package com.tlog.viewmodel.share

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.data.api.SearchTravel
import com.tlog.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
): ViewModel() {
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
                    }
                    catch (e: Exception) {
                        Log.d("SearchViewModel", e.message.toString())
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
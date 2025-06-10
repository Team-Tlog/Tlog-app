package com.tlog.viewmodel.sns

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.SnsPostPreview
import com.tlog.data.repository.SnsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SnsSearchViewModel @Inject constructor(
    private val repository: SnsRepository
): ViewModel() {
    private var _searchText = MutableStateFlow("")
    val searchText = _searchText

    private var _searchResult = mutableStateOf<List<SnsPostPreview>>(emptyList())
    val searchResult: State<List<SnsPostPreview>> = _searchResult

    private var lastPostId: String? = null


    init {
        viewModelScope.launch {
            @OptIn(FlowPreview::class)
            _searchText
                .debounce(500)
                .filter { it.isNotBlank() && it.length >= 1}
                .distinctUntilChanged()
                .collect {
                    try {
                        searchPost(it)
                    }
                    catch (e: Exception) {
                        Log.d("SnsSearchViewModel API", e.message.toString())
                    }
                }
        }
    }


    suspend fun searchPost(searchText: String) {
        try {
            val response = repository.searchPost(query = searchText, size = 10, lastPostId = lastPostId)
            _searchResult.value = response.data.content
            lastPostId = response.data.content[response.data.content.size - 1].postId
        }catch (e: Exception) {
            Log.d("SnsSearchViewModel API", e.message.toString())
        }
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
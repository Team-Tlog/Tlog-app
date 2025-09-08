package com.tlog.viewmodel.sns

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.data.api.SnsPostPreview
import com.tlog.data.model.share.toErrorMessage
import com.tlog.data.repository.SnsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class SnsSearchViewModel @Inject constructor(
    private val repository: SnsRepository
): ViewModel() {
    sealed class UiEvent {
        data class Error(val message: String): UiEvent()
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow
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
                .filter { it.isNotBlank() && it.isNotEmpty()}
                .distinctUntilChanged()
                .collect {
                    searchPost(it)
                }
        }
    }


    suspend fun searchPost(searchText: String) {
        try {
            val response = repository.searchPost(query = searchText, size = 10, lastPostId = lastPostId)

            _searchResult.value = response.data.content
            if (response.data.content.isNotEmpty()) // content가 비었을 때 예외 처리
                lastPostId = response.data.content[response.data.content.size - 1].postId

        } catch (e: HttpException) {
            eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
        } catch (e: Exception) {
            eventFlow.emit(UiEvent.Error(e.toErrorMessage()))
        }
    }


    fun updateSearchText(newSearchText: String) {
        _searchText.value = newSearchText
        if (newSearchText.isBlank())
            _searchResult.value = emptyList()
    }

    fun checkSearchText(): Boolean {
        return searchText.value.isNotBlank()
    }
}
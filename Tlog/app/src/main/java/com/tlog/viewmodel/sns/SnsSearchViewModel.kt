package com.tlog.viewmodel.sns

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.data.api.SnsPostPreview
import com.tlog.data.repository.SnsRepository
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
class SnsSearchViewModel @Inject constructor(
    private val repository: SnsRepository
): BaseViewModel() {

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

        } catch (e: Exception) {
            showToast(e.message ?: "검색 중 오류가 발생했습니다")
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

    fun navToPostDetail(postId: String) {
        navigate(Screen.SnsPostDetail(postId))
    }
}
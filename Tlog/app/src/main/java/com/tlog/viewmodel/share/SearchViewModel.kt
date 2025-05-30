package com.tlog.viewmodel.share

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.RetrofitInstance
import com.tlog.api.SearchApi
import com.tlog.data.model.travel.SearchTravel
import com.tlog.data.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
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

    private var _searchText = MutableStateFlow("")
    val searchText = _searchText

    init {
        viewModelScope.launch {
            @OptIn(FlowPreview::class) // debounce 때문에 사용
            _searchText
                .debounce(500) // 입력 없을 때
                .filter { it.isNotBlank() && it.length >= 1} // 공백 무시 / 길이 1이상
                .distinctUntilChanged()
                .collect { searchTravel(it) }
        }
    }

    suspend fun searchTravel(searchText: String) {
        val response = repository.searchTravel(searchText)
        _searchResult.value = response.data
    }

    fun updateSearchText(newSearchText: String) {
        _searchText.value = newSearchText
    }

    fun checkSearchText(): Boolean {
        return searchText.value.isNotEmpty()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {
    @Provides
    fun provideSearchRepository(
        retrofitInstance: SearchApi
    ): SearchRepository {
        return SearchRepository(retrofitInstance)
    }

    @Provides
    fun provideSearchApi(): SearchApi {
        return RetrofitInstance.getInstance().create(SearchApi::class.java)
    }
}
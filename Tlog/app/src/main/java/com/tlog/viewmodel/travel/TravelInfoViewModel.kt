package com.tlog.viewmodel.travel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.TravelApi
import com.tlog.data.model.travel.TravelDetailResponse
import com.tlog.data.repository.RecommendDestinationRepository
import com.tlog.data.repository.SearchOneDestinationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TravelInfoViewModel @Inject constructor(
    private val searchOneDestinationRepository: SearchOneDestinationRepository
) : ViewModel() {

    private val _destinationDetail = MutableStateFlow<TravelDetailResponse?>(null)
    val destinationDetail: StateFlow<TravelDetailResponse?> = _destinationDetail

    private val _sortOption = MutableStateFlow("추천순")
    val sortOption: StateFlow<String> = _sortOption

    fun updateSelectOption(newOption: String) {
        _sortOption.value = newOption
    }

    fun loadDestinationById(id: String) {
        viewModelScope.launch {
            val response = searchOneDestinationRepository.getDestinationById(id)
            _destinationDetail.value = response
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
object SearchOneDestinationModule {
    @Provides
    fun provideSearchOneDestinationRepository(
        travelApi: TravelApi
    ): SearchOneDestinationRepository {
        return SearchOneDestinationRepository(travelApi)
    }
}
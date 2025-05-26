package com.tlog.viewmodel.travel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.api.RetrofitInstance
import com.tlog.api.TeamApi
import com.tlog.api.TravelApi
import com.tlog.data.model.TravelDestinationData
import com.tlog.data.model.travel.TravelDestinationResponse
import com.tlog.data.repository.MyTeamListRepository
import com.tlog.data.repository.RecommendDestinationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TravelDestinationRecommendationViewModel @Inject constructor(
    private val repository: RecommendDestinationRepository
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow("추천순")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _destinations = MutableStateFlow<List<TravelDestinationResponse>>(emptyList())
    val destinations: StateFlow<List<TravelDestinationResponse>> = _destinations.asStateFlow()

    init {
        loadDestinations()
    }

    fun loadDestinations() {
        viewModelScope.launch {
            try {
                val response = repository.getDestinations(
                )
                _destinations.value = response.content
            } catch (e: Exception) {
                // TODO: 에러 처리
            }
        }
    }

    fun setCategory(category: String) {
        _selectedCategory.value = category

        val sortedList = when (category) {
            "추천순" -> _destinations.value
            "인기순" -> _destinations.value.sortedByDescending { it.averageRating }
            "리뷰순" -> _destinations.value.sortedByDescending { it.reviewCount }
            else -> _destinations.value
        }

        _destinations.value = sortedList
    }

    /*
    fun toggleFavorite(id: String) {
        viewModelScope.launch {
            _destinations.value = _destinations.value.map {
                if (it.id == id) it.copy(isFavorite = !it.isFavorite)
                else it
            }
        }
    }*/
}

@Module
@InstallIn(SingletonComponent::class)
object RecommendDestinationModule {
    @Provides
    fun provideRecommendDestinationRepository(
        travelApi: TravelApi
    ): RecommendDestinationRepository {
        return RecommendDestinationRepository(travelApi)
    }
}

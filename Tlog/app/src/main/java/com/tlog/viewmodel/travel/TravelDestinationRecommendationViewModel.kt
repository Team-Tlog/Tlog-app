package com.tlog.viewmodel.travel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class Destination(
    val id: String,
    val name: String,
    val location: String,
    val rating: Double,
    val reviewCount: Int,
    val tags: List<String>,
    val isFavorite: Boolean = false
)

class TravelDestinationRecommendationViewModel : ViewModel() {
    private val _selectedCategory = MutableStateFlow("추천순")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _originalDestinations = listOf(
        Destination(
            id = "1",
            name = "광안리",
            location = "부산",
            rating = 4.8,
            reviewCount = 1245,
            tags = listOf("바다", "자연"),
            isFavorite = false
        ),
        Destination(
            id = "2",
            name = "강남",
            location = "서울",
            rating = 4.5,
            reviewCount = 3456,
            tags = listOf("도시", "쇼핑"),
            isFavorite = false
        ),
        Destination(
            id = "3",
            name = "서귀포",
            location = "제주도",
            rating = 4.6,
            reviewCount = 2134,
            tags = listOf("바다", "항구"),
            isFavorite = false
        ),
        Destination(
            id = "4",
            name = "수성못",
            location = "대구",
            rating = 4.6,
            reviewCount = 2134,
            tags = listOf("데이트", "핫플"),
            isFavorite = false
        ),
        Destination(
            id = "5",
            name = "해운대",
            location = "부산",
            rating = 4.6,
            reviewCount = 2134,
            tags = listOf("바다", "항구", "데이트"),
            isFavorite = false
        )
    )

    private val _destinations = MutableStateFlow(_originalDestinations)
    val destinations: StateFlow<List<Destination>> = _destinations.asStateFlow()

    fun setCategory(category: String) {
        _selectedCategory.value = category

        val sortedList = when (category) {
            "추천순" -> _originalDestinations // 원본 순서 유지
            "인기순" -> _originalDestinations.sortedByDescending { it.rating } // 평점 높은 순
            "리뷰순" -> _originalDestinations.sortedByDescending { it.reviewCount } // 리뷰 개수 많은 순
            else -> _originalDestinations
        }

        _destinations.value = sortedList
    }



    fun toggleFavorite(id: String) {
        viewModelScope.launch {
            val updatedList = _destinations.value.map { destination ->
                if (destination.id == id) {
                    destination.copy(isFavorite = !destination.isFavorite)
                } else {
                    destination
                }
            }
            _destinations.value = updatedList
        }
    }
}
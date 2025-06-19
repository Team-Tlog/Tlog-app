package com.tlog.viewmodel.review

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.data.local.ScrapManager
import com.tlog.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.plus
import androidx.compose.runtime.State
import com.tlog.data.model.travel.Review
import java.util.Locale


@HiltViewModel
class ReviewListViewModel @Inject constructor(
    private val repository: ReviewRepository,
    private val scrapManager: ScrapManager
): ViewModel() {
    private val _reviewList = mutableStateOf<List<Review>>(emptyList())
    val reviewList: State<List<Review>> = _reviewList

    private val _ratingDistribution = mutableStateOf<Map<String, Int>>(emptyMap())
    val ratingDistribution: State<Map<String, Int>> = _ratingDistribution

    private val _rating = mutableStateOf(0.0)
    val rating: State<Double> = _rating

    private val _sortOption = mutableStateOf("날짜순")
    val sortOption: State<String> = _sortOption

    fun updateSelectOption(newOption: String) {
        _sortOption.value = newOption
    }


    private var page = 0
    private val pageSize = 10
    private val sort = emptyList<String>()
    private var isLastPage = false

    fun loadReviewList(
        id: String,
        sortType: String = when (sortOption.value) {
            "날짜순" -> "RECENT"
            "높은순" -> "HIGH_SCORE"
            "낮은순" -> "LOW_SCORE"
            else -> "RECENT"
        }
    ) {
        viewModelScope.launch {
            try {
                val response = repository.getReviewList(
                    travelId = id,
                    sortType = sortType,
                    page = page,
                    size = pageSize,
                    sort = sort
                )
                _ratingDistribution.value = response.data.ratingDistribution
                _reviewList.value = response.data.reviews.content
                getRating(reviewCount = response.data.ratingDistribution)
            }
            catch (e: Exception) {
                Log.d("ReviewListViewModel", e.message.toString())
            }
        }
    }

    private fun getRating(reviewCount: Map<String, Int>) {
        var sum = 0
        var totalReviews = 0

        for (i in 1..5) {
            val key = i.toString()
            val count = reviewCount[key] ?: 0
            sum += count * i
            totalReviews += count
        }

        _rating.value = if (totalReviews > 0) {
            String.format(Locale.US, "%.2f", sum.toDouble() / totalReviews).toDouble() // 소숫점 2자리
        } else {
            0.0
        }
    }

    fun loadNextPage(
        id: String,
        sortType: String = when (sortOption.value) {
            "날짜순" -> "RECENT"
            "높은순" -> "HIGH_SCORE"
            "낮은순" -> "LOW_SCORE"
            else -> "RECENT"
        }
    ) {
        if (isLastPage == true) return
        page++
        viewModelScope.launch {
            try {
                val response = repository.getReviewList(
                    travelId = id,
                    sortType = sortType,
                    page = page,
                    size = pageSize,
                    sort = sort
                )
                Log.d("ReviewListViewModel", response.data.toString())

                isLastPage = response.data.reviews.last
                _reviewList.value += response.data.reviews.content

            } catch (e: Exception) {
                Log.d("ReviewListViewModel", "에러 로그 : ${e.message}")
            }
        }
    }

    // scrap
    fun toggleScrap(destinationId: String) {
        viewModelScope.launch {
            try {
                scrapManager.toggleScrap(destinationId)
            } catch (e: Exception) {
                Log.d("ReviewListViewModel", "스크랩 에러 로그 : ${e.message}")
            }
        }
    }

    fun isScraped(destinationId: String): Boolean {
        return scrapManager.isScraped(destinationId)
    }
}
package com.tlog.viewmodel.review

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlog.data.api.ReviewResponse
import com.tlog.data.local.ScrapManager
import com.tlog.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.plus
import androidx.compose.runtime.State
import com.tlog.data.model.travel.DetailReview


@HiltViewModel
class ReviewListViewModel @Inject constructor(
    private val repository: ReviewRepository,
    private val scrapManager: ScrapManager
): ViewModel() {
    private val _reviewList = mutableStateOf<List<DetailReview>>(emptyList())
    val reviewList: State<List<DetailReview>> = _reviewList


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
                Log.d("ReviewListViewModel", response.content.toString())
                _reviewList.value = response.content
            }
            catch (e: Exception) {
                Log.d("ReviewListViewModel", e.message.toString())
            }
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
                Log.d("ReviewListViewModel", response.content.toString())

                isLastPage = response.last
                _reviewList.value += response.content
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
                // TODO: Error handling
            }
        }
    }

    fun isScraped(destinationId: String): Boolean {
        return scrapManager.isScraped(destinationId)
    }
}
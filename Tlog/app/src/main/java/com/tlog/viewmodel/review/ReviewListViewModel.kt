package com.tlog.viewmodel.review

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
import androidx.compose.runtime.mutableDoubleStateOf
import com.tlog.data.model.share.toErrorMessage
import com.tlog.data.model.travel.Review
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import retrofit2.HttpException
import java.util.Locale


@HiltViewModel
class ReviewListViewModel @Inject constructor(
    private val repository: ReviewRepository,
    private val scrapManager: ScrapManager
): ViewModel() {

    sealed interface NavTarget {
        data class ReviewWrite(val travelId: String, val travelName: String): NavTarget
        data class SnsMyPage(val userId: String): NavTarget
    }
    sealed interface UiEvent {
        data class Navigate(val target: NavTarget): UiEvent
        data class ShowToast(val message: String): UiEvent
    }

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _reviewList = mutableStateOf<List<Review>>(emptyList())
    val reviewList: State<List<Review>> = _reviewList

    private val _ratingDistribution = mutableStateOf<Map<String, Int>>(emptyMap())
    val ratingDistribution: State<Map<String, Int>> = _ratingDistribution

    private val _rating = mutableDoubleStateOf(0.0)
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

    fun getReviewList(
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
            catch (e: HttpException) {
                _uiEvent.trySend(UiEvent.ShowToast("[리뷰] ${e.toErrorMessage()}"))
            } catch (e: Exception) {
                _uiEvent.trySend(UiEvent.ShowToast("[리뷰] ${e.toErrorMessage()}"))
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

        _rating.doubleValue = if (totalReviews > 0) {
            String.format(Locale.US, "%.2f", sum.toDouble() / totalReviews).toDouble() // 소숫점 2자리
        } else {
            0.0
        }
    }

    fun getNextPage(
        id: String,
        sortType: String = when (sortOption.value) {
            "날짜순" -> "RECENT"
            "높은순" -> "HIGH_SCORE"
            "낮은순" -> "LOW_SCORE"
            else -> "RECENT"
        }
    ) {
        if (isLastPage) return
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

                isLastPage = response.data.reviews.last
                _reviewList.value += response.data.reviews.content

            } catch (e: HttpException) {
                _uiEvent.trySend(UiEvent.ShowToast("[리뷰] ${e.toErrorMessage()}"))
            } catch (e: Exception) {
                _uiEvent.trySend(UiEvent.ShowToast("[리뷰] ${e.toErrorMessage()}"))
            }
        }
    }

    fun toggleScrap(destinationId: String) {
        viewModelScope.launch {
            try {
                scrapManager.toggleScrap(destinationId)
            } catch (e: HttpException) {
                _uiEvent.trySend(UiEvent.ShowToast("[스크랩] ${e.toErrorMessage()}"))
            } catch (e: Exception) {
                _uiEvent.trySend(UiEvent.ShowToast("[스크랩] ${e.toErrorMessage()}"))
            }
        }
    }

    fun isScraped(destinationId: String): Boolean {
        return scrapManager.isScraped(destinationId)
    }

    fun navToReviewWrite(travelId: String, travelName: String) {
        _uiEvent.trySend(UiEvent.Navigate(NavTarget.ReviewWrite(travelId, travelName)))
    }

    fun navToSnsMyPage(userId: String) {
        _uiEvent.trySend(UiEvent.Navigate(NavTarget.SnsMyPage(userId)))
    }
}
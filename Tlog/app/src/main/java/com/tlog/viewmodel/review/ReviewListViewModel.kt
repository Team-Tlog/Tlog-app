package com.tlog.viewmodel.review

import androidx.compose.runtime.mutableStateOf
import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.data.local.ScrapManager
import com.tlog.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.collections.plus
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import com.tlog.data.model.travel.Review
import com.tlog.ui.navigation.Screen
import java.util.Locale


@HiltViewModel
class ReviewListViewModel @Inject constructor(
    private val repository: ReviewRepository,
    private val scrapManager: ScrapManager
): BaseViewModel() {

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

    fun resetPaging() {
        page = 0
        isLastPage = false
        _reviewList.value = emptyList()
    }
    fun getReviewList(
        id: String,
        sortType: String = when (sortOption.value) {
            "날짜순" -> "RECENT"
            "높은순" -> "HIGH_SCORE"
            "낮은순" -> "LOW_SCORE"
            else -> "RECENT"
        }
    ) {
        launchSafeCall(
            action = {
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
            },
            onError = { showToast("[리뷰] $it") }
        )
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
        launchSafeCall(
            action = {
                val response = repository.getReviewList(
                    travelId = id,
                    sortType = sortType,
                    page = page,
                    size = pageSize,
                    sort = sort
                )

                isLastPage = response.data.reviews.last
                _reviewList.value += response.data.reviews.content
            },
            onError = { showToast("[리뷰] $it") }
        )
    }

    fun toggleScrap(destinationId: String) {
        launchSafeCall(
            action = {
                scrapManager.toggleScrap(destinationId)
            },
            onError = { showToast("[스크랩] $it") }
        )
    }

    fun isScraped(destinationId: String): Boolean {
        return scrapManager.isScraped(destinationId)
    }

    fun navToReviewWrite(travelId: String, travelName: String) {
        navigate(Screen.ReviewWrite(travelId, travelName))
    }

    fun navToSnsMyPage(userId: String) {
        navigate(Screen.SnsMyPage(userId))
    }
}
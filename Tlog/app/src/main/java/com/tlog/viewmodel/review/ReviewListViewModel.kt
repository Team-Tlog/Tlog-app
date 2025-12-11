package com.tlog.viewmodel.review

import com.tlog.viewmodel.base.BaseViewModel
import com.tlog.data.local.ScrapManager
import com.tlog.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.collections.plus
import com.tlog.data.dto.travel.ReviewDto
import com.tlog.domain.model.travel.review.Review
import com.tlog.ui.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

@HiltViewModel
class ReviewListViewModel @Inject constructor(
    private val repository: ReviewRepository,
    private val scrapManager: ScrapManager
): BaseViewModel() {
    val scraps = scrapManager.scrapList

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews = _reviews.asStateFlow()

    private val _ratingDistribution = MutableStateFlow<Map<String, Int>>(emptyMap())
    val ratingDistribution = _ratingDistribution.asStateFlow()

    private val _rating = MutableStateFlow(0.0)
    val rating = _rating.asStateFlow()

    private val _sortOption = MutableStateFlow("날짜순")
    val sortOption = _sortOption.asStateFlow()

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
        _reviews.value = emptyList()
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
                repository.getReviewList(
                    travelId = id,
                    sortType = sortType,
                    page = page,
                    size = pageSize,
                    sort = sort
                )
            },
            onSuccess = {
                _ratingDistribution.value = it.first.ratingDistribution
                _reviews.value = it.first.reviews
                getRating(reviewCount = it.first.ratingDistribution)
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

        _rating.value = if (totalReviews > 0) {
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

                isLastPage = response.second
                _reviews.value += response.first.reviews
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

    fun navToReviewWrite(travelId: String, travelName: String) {
        navigate(Screen.ReviewWrite(travelId, travelName))
    }

    fun navToSnsMyPage(userId: String) {
        navigate(Screen.SnsMyPage(userId))
    }
}

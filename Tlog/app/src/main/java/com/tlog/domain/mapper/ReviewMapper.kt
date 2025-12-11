package com.tlog.domain.mapper

import com.tlog.data.dto.response.review.ReviewsResponse
import com.tlog.data.dto.travel.ReviewDto
import com.tlog.domain.model.travel.review.Review
import com.tlog.domain.model.travel.review.ReviewSummary
import com.tlog.util.toFormattedDate

fun ReviewDto.toDomain(): Review {
    return Review(
        id = id,
        userId = userId,
        username = username,
        userProfileImageUrl = userProfileImageUrl,
        rating = rating,
        content = content,
        reviewImageUrl = reviewImageUrl,
        createdAt = createdAt.toFormattedDate()
    )
}

fun ReviewsResponse.toDomain(): ReviewSummary {
    return ReviewSummary(
        ratingDistribution = ratingDistribution,
        reviews = reviews.content.map { it.toDomain() }
    )
}

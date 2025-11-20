package com.tlog.data.api

import com.tlog.data.model.travel.Review
import com.tlog.data.model.share.Location
import com.tlog.data.model.share.TagCount
import com.tlog.data.model.travel.MinimalTravel

data class ReviewListResponse(
    val ratingDistribution: Map<String, Int>,
    val reviews: ReviewList
)

data class ReviewRequest(
    val userId: String,
    val destinationId: String,
    val username: String,
    val rating: Int,
    val content: String,
    val imageUrlList: List<String>,
    val customTagNames: List<String>
)

data class AddTravelRequest(
    val creater: String,
    val name: String,
    val address: String,
    val location: Location,
    val city: String,
    val district: String,
    val hasParking: Boolean,
    val petFriendly: Boolean,
    val imageUrl: String,
    val description: String,
    val customTags: List<String>
)

data class TravelDetailResponse(
    val id: String,
    val name: String,
    val address: String,
    val location: Location,
    val city: String,
    val description: String,
    val district: String,
    val hasParking: Boolean,
    val petFriendly: Boolean,
    val ratingSum: Int,
    val reviewCount: Int,
    val averageRating: Double,
    val imageUrl: String,
    val topTags: List<TagCount>,
    val ratingDistribution: Map<String, Int>,
    val top2Reviews: List<Review>,
    val relatedDestinations: List<MinimalTravel>
)

data class TravelRecommendPagedResponse(
    val content: List<TravelDestinationResponse>,
    val number: Int,
    val sort: Sort,
    val pageable: Pageable,
    val paged: Boolean,
    val pageNumber: Int,
    val pageSize: Int,
    val unpaged: Boolean,
    val numberOfElements: Int,
    val first: Boolean,
    val last: Boolean,
    val empty: Boolean
)

data class TravelDestinationResponse(
    val id: String,
    val name: String,
    val city: String,
    val location: Location,
    val reviewCount: Int,
    val averageRating: Double,
    val imageUrl: String,
    val tagCountList: List<TagCount>
)

data class BannerTravelResponse(
    val title: String,
    val destinations: BannerDestinationsWrapper
)

data class BannerDestinationsWrapper(
    val content: List<TravelDestinationResponse>
)

data class ReviewList(
    val content: List<Review>,
    val pageable: Pageable,
    val size: Int,
    val number: Int,
    val sort: Sort,
    val numberOfElements: Int,
    val first: Boolean,
    val last: Boolean,
    val empty: Boolean
)

// 코스 목록
data class CourseItem(
    val id: String,
    val startDate: String,
    val endDate: String,
    val dates: List<DateItem>
)

data class DateItem(
    val destinationGroups: List<Destinations>
)

data class Destinations(
    val groupName: String,
    val destinations: List<CoursesDestination>
)

data class CoursesDestination(
    val id: String,
    val name: String,
    val city: String,
    val location: Location,
    val reviewCount: Int,
    val averageRating: Int,
    val imageUrl: String?,
    val description: String,
    val tagCountList: List<TagCount>?
)


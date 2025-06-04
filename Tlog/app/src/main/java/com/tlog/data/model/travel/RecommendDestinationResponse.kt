package com.tlog.data.model.travel


// 전체 여행지 조회
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


data class Location(
    val longitude: String,
    val latitude: String
)

data class TagCount(
    val tag: String,
    val count: Int
)

data class Pageable(
    val pageNumber: Int,
    val pageSize: Int,
    val sort: Sort,
    val offset: Int,
    val sorted: Boolean,
    val unsorted: Boolean
)

data class Sort(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)


// 여행지 추가
data class AddTravelRequest(
    val creater: String,
    val name: String,
    val address: String,
    val location: com.tlog.data.model.Location,
    val city: String,
    val district: String,
    val hasParking: Boolean,
    val petFriendly: Boolean,
    val imageUrl: String,
    val description: String,
    val customTags: List<String>
)
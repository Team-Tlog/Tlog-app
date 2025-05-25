package com.tlog.data.model.travel

data class TravelRecommendPagedResponse<T>(
    val totalElements: Long,
    val totalPages: Int,
    val size: Int,
    val content: List<TravelDestinationResponse>,
    val number: Int,
    val sort: SortInfo,
    val pageable: PageableInfo,
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
    val averageRating: Int,
    val imageUrl: String,
    val tagCountList: List<TagCount>
)

data class Location(
    val longitude: String,
    val latitude: String
)

data class TagCount(
    val tagName: String,
    val count: Int
)

data class SortInfo(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)

data class PageableInfo(
    val offset: Int,
    val sort: SortInfo,
    val paged: Boolean,
    val pageNumber: Int,
    val pageSize: Int,
    val unpaged: Boolean
)

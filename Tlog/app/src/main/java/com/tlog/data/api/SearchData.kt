package com.tlog.data.api

import com.tlog.data.model.share.Location
import com.tlog.data.model.share.TagCount


data class SearchResponse(
    val content: List<TravelDestinationResponse>,
    val pageable: Pageable,
    val totalPages: Int,
    val totalElements: Int,
    val last: Boolean,
    val size: Int,
    val number: Int,
    val sort: Sort,
    val numberOfElements: Int,
    val first: Boolean,
    val empty: Boolean,
)





data class SearchTravel(
    val id: String,
    val name: String,
    val city: String,
    val location: Location,
    val reviewCount: Int,
    val averageRating: Double,
    val imageUrl: String?,
    val description: String,
    val tagCountList: List<TagCount> = emptyList(),
)

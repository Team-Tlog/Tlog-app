package com.tlog.data.api

import com.tlog.api.Pageable
import com.tlog.data.model.travel.TravelDestinationResponse

data class SearchAndPageResponse(
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

data class Sort(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)
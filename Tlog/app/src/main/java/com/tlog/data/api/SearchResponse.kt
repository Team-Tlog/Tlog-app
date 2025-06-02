package com.tlog.data.api

import com.tlog.data.model.travel.Pageable
import com.tlog.data.model.travel.Sort
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


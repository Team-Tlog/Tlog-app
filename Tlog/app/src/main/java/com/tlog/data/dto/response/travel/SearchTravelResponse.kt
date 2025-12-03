package com.tlog.data.dto.response.travel

import com.tlog.data.dto.response.page.Pageable
import com.tlog.data.dto.response.page.Sort

data class SearchTravelResponse(
    val content: List<TravelSearch>,
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

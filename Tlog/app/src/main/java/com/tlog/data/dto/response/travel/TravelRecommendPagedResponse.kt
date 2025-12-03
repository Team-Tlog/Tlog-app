package com.tlog.data.dto.response.travel

import com.tlog.data.dto.response.page.Pageable
import com.tlog.data.dto.response.page.Sort

data class TravelRecommendPagedResponse(
    val content: List<TravelDestination>,
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

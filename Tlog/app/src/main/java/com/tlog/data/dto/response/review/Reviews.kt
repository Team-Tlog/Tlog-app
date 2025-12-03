package com.tlog.data.dto.response.review

import com.tlog.data.dto.response.page.Pageable
import com.tlog.data.dto.response.page.Sort
import com.tlog.data.dto.travel.ReviewDto

data class Reviews(
    val content: List<ReviewDto>,
    val pageable: Pageable,
    val size: Int,
    val number: Int,
    val sort: Sort,
    val numberOfElements: Int,
    val first: Boolean,
    val last: Boolean,
    val empty: Boolean
)

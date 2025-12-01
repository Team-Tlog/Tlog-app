package com.tlog.data.model.response.review

import com.tlog.data.model.response.page.Pageable
import com.tlog.data.model.response.page.Sort
import com.tlog.data.model.travel.Review

data class Reviews(
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

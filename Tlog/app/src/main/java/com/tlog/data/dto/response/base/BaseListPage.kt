package com.tlog.data.dto.response.base

import com.tlog.data.dto.response.page.Pageable
import com.tlog.data.dto.response.page.Sort

data class BaseListPage<T> (
    val content: T,
    val pageable: Pageable,
    val offset: Int,
    val size: Int,
    val number: Int,
    val sort: Sort,
    val totalElements: Int,
    val last: Boolean,
    val numberOfElements: Int,
    val first: Boolean,
    val empty: Boolean,
)
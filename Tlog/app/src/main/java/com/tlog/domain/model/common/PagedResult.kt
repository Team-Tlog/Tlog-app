package com.tlog.domain.model.common

data class PagedResult<T>(
    val items: List<T>,
    val isLastPage: Boolean
)

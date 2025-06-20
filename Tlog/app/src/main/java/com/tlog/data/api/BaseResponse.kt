package com.tlog.data.api


data class BaseResponse<T>(
    val status: Int,
    val message: String,
    val data: T
)

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

data class MinimalListPage<T> (
    val totalElements: Int,
    val totalPages: Int,
    val size: Int,
    val content: List<T>
)


data class BaseListResponse<T>(
    val ststus: Int,
    val message: String,
    val data: BaseListPage<T>
)
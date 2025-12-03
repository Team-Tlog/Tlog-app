package com.tlog.data.dto.response.base

data class BaseListResponse<T>(
    val status: Int,
    val message: String,
    val data: BaseListPage<T>
)

package com.tlog.data.model.response.base

data class BaseListResponse<T>(
    val status: Int,
    val message: String,
    val data: BaseListPage<T>
)

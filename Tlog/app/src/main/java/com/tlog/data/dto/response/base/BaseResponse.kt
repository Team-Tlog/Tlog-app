package com.tlog.data.dto.response.base


data class BaseResponse<T>(
    val status: Int,
    val message: String,
    val data: T
)

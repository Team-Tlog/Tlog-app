package com.tlog.data.api

data class BaseResponse<T>(
    val status: Int,
    val message: String,
    val data: T
)
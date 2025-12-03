package com.tlog.data.dto.response.sns

data class SnsUser(
    val uuid: String,
    val name: String,
    val snsName: String,
    val profileImageUrl: String,
    val tbtiValue: Int
)
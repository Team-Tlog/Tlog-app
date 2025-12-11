package com.tlog.data.dto.request.sns

data class FollowRequest(
    val from_userId: String,
    val to_userId: String
)

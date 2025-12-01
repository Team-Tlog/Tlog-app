package com.tlog.data.model.request.sns

data class FollowRequest(
    val from_userId: String,
    val to_userId: String
)

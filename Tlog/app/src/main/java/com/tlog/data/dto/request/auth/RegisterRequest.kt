package com.tlog.data.dto.request.auth

data class RegisterRequest(
    val type: String,
    val accessToken: String,
    val userProfile: UserProfileBody,
    val preferTagIds: List<Int>
)

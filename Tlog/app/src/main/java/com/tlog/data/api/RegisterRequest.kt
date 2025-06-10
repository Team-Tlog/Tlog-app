package com.tlog.data.api

data class RegisterRequest(
    val type: String,
    val accessToken: String,
    val userProfile: UserProfileDto
)

data class UserProfileDto(
    val tbtiValue: String
)
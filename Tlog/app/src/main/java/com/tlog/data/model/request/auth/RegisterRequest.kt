package com.tlog.data.model.request.auth

data class RegisterRequest(
    val type: String,
    val accessToken: String,
    val userProfile: UserProfile,
    val preferTagIds: List<Int>
)

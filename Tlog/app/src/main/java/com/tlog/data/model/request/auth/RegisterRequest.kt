package com.tlog.data.model.request.auth

import com.tlog.data.model.request.auth.UserProfile

data class RegisterRequest(
    val type: String,
    val accessToken: String,
    val userProfile: UserProfile,
    val preferTagIds: List<Int>
)

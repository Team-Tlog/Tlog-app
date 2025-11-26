package com.tlog.data.model.request.auth

import com.tlog.data.dto.UserProfileDto

data class RegisterRequest(
    val type: String,
    val accessToken: String,
    val userProfile: UserProfileDto,
    val preferTagIds: List<Int>
)

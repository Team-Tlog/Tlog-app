package com.tlog.data.dto.request.auth

data class LoginRequest(
    val type: String,
    val accessToken: String
)

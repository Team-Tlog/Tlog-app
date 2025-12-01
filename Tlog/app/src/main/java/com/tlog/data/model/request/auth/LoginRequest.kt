package com.tlog.data.model.request.auth

data class LoginRequest(
    val type: String,
    val accessToken: String
)

package com.tlog.data.api

data class LoginRequest(
    val type: String, // "KAKAO" or "GOOGLE"
    val accessToken: String
)

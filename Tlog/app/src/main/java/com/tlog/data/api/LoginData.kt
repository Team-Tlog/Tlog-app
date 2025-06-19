package com.tlog.data.api

data class LoginRequest(
    val type: String,
    val accessToken: String
)





data class FirebaseTokenData(
    val firebaseCustomToken: String
)

data class FcmTokenBody(
    val userId: String,
    val firebaseToken: String
)

data class RegisterRequest(
    val type: String,
    val accessToken: String,
    val userProfile: UserProfileDto
)

data class UserProfileDto(
    val tbtiValue: String
)
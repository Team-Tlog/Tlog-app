package com.tlog.data.dto.request.auth

data class FcmTokenRequest(
    val userId: String,
    val firebaseToken: String
)

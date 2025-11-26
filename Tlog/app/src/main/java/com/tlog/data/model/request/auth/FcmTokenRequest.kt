package com.tlog.data.model.request.auth

data class FcmTokenRequest(
    val userId: String,
    val firebaseToken: String
)

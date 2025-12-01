package com.tlog.data.local

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TokenProvider @Inject constructor() {
    private var userId: String? = null
    private var accessToken: String? = null
    private var refreshToken: String? = null
    private var firebaseCustomToken: String? = null
    private var snsId: String? = null

    fun getUserId(): String? = userId
    fun getAccessToken(): String? = accessToken
    fun getRefreshToken(): String? = refreshToken
    fun getFirebaseCustomToken(): String? = firebaseCustomToken
    fun getSnsId(): String? = snsId

    fun setUserId(id: String?) {
        userId = id
    }

    fun setAccessToken(token: String?) {
        accessToken = token
    }

    fun setRefreshToken(token: String?) {
        refreshToken = token
    }

    fun setFirebaseCustomToken(token: String?) {
        firebaseCustomToken = token
    }

    fun setSnsId(id: String?) {
        snsId = id
    }
}

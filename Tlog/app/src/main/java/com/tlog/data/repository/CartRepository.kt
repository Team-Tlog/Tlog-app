package com.tlog.data.repository

import com.tlog.api.UserApi
import com.tlog.data.model.travel.Travel

class CartRepository(
    private val retrofitInstance: UserApi
) {
    suspend fun getUserCart(userId: String): List<Travel> {
        return retrofitInstance.getUserCart(userId).data
    }
}
package com.tlog.data.repository

import com.tlog.api.TravelApi
import com.tlog.api.UserApi
import com.tlog.data.api.ScrapDestinationResponse
import com.tlog.data.model.travel.Travel
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val userApi: UserApi,
    private val travelApi: TravelApi
) {
    suspend fun getUserCart(userId: String): List<Travel> {
        return userApi.getUserCart(userId).data
    }

    suspend fun getUserScrap(userId: String): List<ScrapDestinationResponse> {
        return travelApi.getUserScraps(userId).data
    }
}
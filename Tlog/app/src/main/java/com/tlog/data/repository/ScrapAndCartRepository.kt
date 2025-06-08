package com.tlog.data.repository

import com.tlog.api.TravelApi
import com.tlog.api.UserApi
import com.tlog.data.api.ScrapDestinationResponse
import com.tlog.data.model.travel.ShopCart
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ScrapAndCartRepository @Inject constructor(
    private val userApi: UserApi,
    private val travelApi: TravelApi
) {
    suspend fun getUserCart(userId: String): List<ShopCart> {
        return userApi.getUserCart(userId).data
    }

    suspend fun getUserScrap(userId: String): List<ScrapDestinationResponse> {
        return travelApi.getUserScraps(userId).data
    }

    suspend fun deleteScrapDestination(userId: String, destId: String) {
        travelApi.deleteScrapDestination(userId, destId)
    }

    suspend fun addDestinationToCart(userId: String, destinationId: String) {
        val plainBody: RequestBody = destinationId.toRequestBody("text/plain".toMediaTypeOrNull())
        userApi.addDestinationToCart(userId, plainBody)
    }

    suspend fun deleteTravelFromCart(userId: String, destId: String) {
        userApi.deleteTravelFromCart(userId, destId)
    }
}
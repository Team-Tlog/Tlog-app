package com.tlog.data.repository

import com.tlog.api.ScrapAndCartApi
import com.tlog.domain.mapper.toDomain
import com.tlog.domain.model.travel.ViewTravel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ScrapAndCartRepository @Inject constructor(
    private val scrapAndCartApi: ScrapAndCartApi
) {
    // scrap
    suspend fun getUserScrap(userId: String): List<ViewTravel> {
        return scrapAndCartApi.getUserScraps(userId).data.map {
            it.toDomain()
        }
    }

    suspend fun deleteScrapDestination(userId: String, destId: String) {
        scrapAndCartApi.deleteScrapDestination(userId, destId)
    }

    // cart
    suspend fun getUserCart(userId: String): List<ViewTravel> {
        return scrapAndCartApi.getUserCart(userId).data.map {
            it.toDomain()
        }
    }
    suspend fun addTravelToCart(userId: String, destinationId: String) {
        val plainBody: RequestBody = destinationId.toRequestBody("text/plain".toMediaTypeOrNull())
        scrapAndCartApi.addTravelToCart(userId, plainBody)
    }

    suspend fun deleteTravelFromCart(userId: String, destId: String) {
        scrapAndCartApi.deleteTravelFromCart(userId, destId)
    }
}

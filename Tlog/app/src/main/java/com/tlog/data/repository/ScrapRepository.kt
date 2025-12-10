package com.tlog.data.repository

import com.tlog.api.ScrapAndCartApi
import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.domain.mapper.toDomain
import com.tlog.domain.model.travel.ScrapTravel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ScrapRepository @Inject constructor(
    private val retrofitInstance: ScrapAndCartApi
) {
    suspend fun scrapDestination(userId: String, destinationId: String): BaseResponse<Unit> {
        val plainBody: RequestBody = destinationId.toRequestBody("text/plain".toMediaTypeOrNull())
        return retrofitInstance.scrapDestination(userId, plainBody)
    }

    suspend fun deleteScrapDestination(userId: String, destinationId: String): BaseResponse<Unit> {
        return retrofitInstance.deleteScrapDestination(userId, destinationId)
    }

    suspend fun getUserScraps(userId: String): List<ScrapTravel> {
        return retrofitInstance.getUserScraps(userId).data.map {
            it.toDomain()
        }
    }
}

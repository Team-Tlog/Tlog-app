package com.tlog.data.repository

import com.tlog.api.ScrapApi
import com.tlog.data.api.BaseResponse
import com.tlog.data.model.travel.Scrap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ScrapRepository @Inject constructor(
    private val retrofitInstance: ScrapApi
){
    suspend fun scrapDestination(userId: String, destinationId: String): BaseResponse<Unit> {
        val plainBody: RequestBody = destinationId.toRequestBody("text/plain".toMediaTypeOrNull())
        return retrofitInstance.scrapDestination(userId, plainBody)
    }

    suspend fun deleteScrapDestination(userId: String, destinationId: String): BaseResponse<Unit> {
        return retrofitInstance.deleteScrapDestination(userId, destinationId)
    }

    suspend fun getUserScraps(userId: String): BaseResponse<List<Scrap>> {
        return retrofitInstance.getUserScraps(userId)
    }
}
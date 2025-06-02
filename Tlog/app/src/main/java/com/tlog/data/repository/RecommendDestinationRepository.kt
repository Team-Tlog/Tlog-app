package com.tlog.data.repository

import android.util.Log
import com.tlog.api.Pageable
import com.tlog.api.SearchApi
import com.tlog.api.TravelApi
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.ScrapDestinationResponse
import com.tlog.data.api.SearchAndPageResponse
import com.tlog.data.model.travel.TravelDestinationResponse
import com.tlog.data.model.travel.TravelRecommendPagedResponse
import jakarta.inject.Inject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class RecommendDestinationRepository @Inject constructor(
    private val travelRetrofitInstance: TravelApi,
    private val searchRetrofitInstance: SearchApi
) {
    suspend fun getDestinations(): TravelRecommendPagedResponse<TravelDestinationResponse>{
        val result = travelRetrofitInstance.getDestinations(
            page = 0,
            size = 5,
            sort = "rating,desc"
        )
        Log.d("RecommendDestinationRepository", "getDestination: $result")
        return result
    }

    suspend fun scrapDestination(userId: String, destinationId: String): BaseResponse<Unit> {
        val plainBody: RequestBody = destinationId.toRequestBody("text/plain".toMediaTypeOrNull())
        return travelRetrofitInstance.scrapDestination(userId, plainBody)
    }

    suspend fun deleteScrapDestination(userId: String, destinationId: String): BaseResponse<Unit> {
        return travelRetrofitInstance.deleteScrapDestination(userId, destinationId)
    }

    suspend fun getUserScraps(userId: String): BaseResponse<List<ScrapDestinationResponse>> {
        return travelRetrofitInstance.getUserScraps(userId)
    }
    
    suspend fun getSearchToCity(pageable: Pageable, query: String): BaseResponse<SearchAndPageResponse> {
        return searchRetrofitInstance.getTravelListByCity(pageable, query)
    }
}


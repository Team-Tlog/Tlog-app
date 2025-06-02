package com.tlog.data.repository

import com.tlog.api.SearchApi
import com.tlog.api.TravelApi
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.ScrapDestinationResponse
import com.tlog.data.api.SearchAndPageResponse
import com.tlog.data.model.travel.TravelRecommendPagedResponse
import jakarta.inject.Inject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class RecommendDestinationRepository @Inject constructor(
    private val travelRetrofitInstance: TravelApi,
    private val searchRetrofitInstance: SearchApi
) {
    suspend fun getDestinations(
        page: Int,
        size: Int,
        sort: List<String>,
        city: String,
        sortType: String? = null,
        tbti: String? = null
    ): BaseResponse<TravelRecommendPagedResponse>{
        return travelRetrofitInstance.getDestinations(
            page = page,
            size = size,
            sort = sort,
            city = city,
            sortType = sortType,
            tbti = tbti
        )
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
    
    suspend fun getSearchToCity(page: Int, size: Int, sort: List<String>, query: String): BaseResponse<SearchAndPageResponse> {
        return searchRetrofitInstance.getTravelListByCity(
            page = page,
            size = size,
            sort = sort,
            city = query
        )
    }
}


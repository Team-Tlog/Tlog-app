package com.tlog.data.repository

import com.tlog.api.SearchApi
import com.tlog.api.TravelApi
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.ScrapDestinationResponse
import com.tlog.data.api.SearchAndPageResponse
import com.tlog.data.model.travel.TravelRecommendPagedResponse
import jakarta.inject.Inject

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
    
    suspend fun getSearchToCity(page: Int, size: Int, sort: List<String>, query: String): BaseResponse<SearchAndPageResponse> {
        return searchRetrofitInstance.getTravelListByCity(
            page = page,
            size = size,
            sort = sort,
            city = query
        )
    }
}


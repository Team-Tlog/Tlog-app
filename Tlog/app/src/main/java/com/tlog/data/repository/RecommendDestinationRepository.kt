package com.tlog.data.repository

import com.tlog.api.SearchApi
import com.tlog.api.TravelApi
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.SearchResponse
import com.tlog.data.api.TravelRecommendPagedResponse
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
    
    suspend fun getSearchToCity(page: Int, size: Int, sort: List<String>, query: String): BaseResponse<SearchResponse> {
        return searchRetrofitInstance.getTravelListByCity(
            page = page,
            size = size,
            sort = sort,
            city = query
        )
    }
}


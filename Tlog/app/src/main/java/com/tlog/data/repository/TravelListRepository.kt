package com.tlog.data.repository

import com.tlog.api.SearchApi
import com.tlog.api.TravelApi
import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.data.dto.response.travel.SearchTravelResponse
import com.tlog.data.dto.response.travel.TravelRecommendPagedResponse
import com.tlog.domain.mapper.toDomain
import com.tlog.domain.model.common.PagedResult
import com.tlog.domain.model.travel.Travel
import jakarta.inject.Inject

class TravelListRepository @Inject constructor(
    private val travelRetrofitInstance: TravelApi,
    private val searchRetrofitInstance: SearchApi
) {
    suspend fun getTravelList(
        page: Int,
        size: Int,
        sort: List<String>,
        city: String,
        sortType: String? = null
    ): PagedResult<Travel> {
        val response = travelRetrofitInstance.getDestinations(
            page = page,
            size = size,
            sort = sort,
            city = city,
            sortType = sortType
        )

        return PagedResult(
            items = response.data.content.toDomain(),
            isLastPage = response.data.last
        )
    }
    
    suspend fun getSearchToCity(page: Int, size: Int, sort: List<String>, query: String): BaseResponse<SearchTravelResponse> {
        return searchRetrofitInstance.searchTravelListByCity(
            page = page,
            size = size,
            sort = sort,
            city = query
        )
    }
}

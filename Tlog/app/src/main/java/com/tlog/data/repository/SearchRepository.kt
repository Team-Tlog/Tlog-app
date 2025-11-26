package com.tlog.data.repository

import com.tlog.api.SearchApi
import com.tlog.data.model.response.base.BaseResponse
import com.tlog.data.model.response.travel.PopularDestination
import com.tlog.data.model.response.travel.TravelDestination
import com.tlog.data.model.response.travel.TravelSearch
import javax.inject.Inject

class SearchRepository @Inject constructor (
    private val retrofitInstance: SearchApi
) {
    suspend fun searchTravel(searchText: String): BaseResponse<List<TravelSearch>> {
        return retrofitInstance.searchTravelListByName(searchText)
    }

    suspend fun getPopularDestinations(): BaseResponse<List<PopularDestination>> {
        return retrofitInstance.getPopularDestinations()
    }

}
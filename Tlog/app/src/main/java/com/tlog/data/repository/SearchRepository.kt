package com.tlog.data.repository

import com.tlog.api.SearchApi
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.SearchTravel

class SearchRepository(
    private val retrofitInstance: SearchApi
) {
    suspend fun searchTravel(searchText: String): BaseResponse<List<SearchTravel>> {
        return retrofitInstance.getTravelListByName(searchText)
    }

}
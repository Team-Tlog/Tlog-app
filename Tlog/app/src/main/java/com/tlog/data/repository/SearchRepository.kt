package com.tlog.data.repository

import com.tlog.api.MinimalTravel
import com.tlog.api.SearchApi
import com.tlog.data.api.BaseResponse

class SearchRepository(
    private val retrofitInstance: SearchApi
) {
    suspend fun searchTravel(searchText: String): BaseResponse<List<MinimalTravel>> {
        return retrofitInstance.getTravelListByName(searchText)
    }

}
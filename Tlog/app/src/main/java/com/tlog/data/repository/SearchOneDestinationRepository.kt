package com.tlog.data.repository

import com.tlog.api.TravelApi
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.TravelDetailResponse
import javax.inject.Inject

class SearchOneDestinationRepository @Inject constructor(
    private val travelApi: TravelApi
) {
    suspend fun getDestinationById(id: String): BaseResponse<TravelDetailResponse> {
        return travelApi.getDestinationById(id)
    }
}
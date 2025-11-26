package com.tlog.data.repository

import com.tlog.api.TravelApi
import com.tlog.data.model.response.base.BaseResponse
import com.tlog.data.model.response.travel.TravelDetailResponse
import javax.inject.Inject

class SearchOneDestinationRepository @Inject constructor(
    private val travelApi: TravelApi
) {
    suspend fun getDestinationById(id: String): BaseResponse<TravelDetailResponse> {
        return travelApi.getDestinationById(id)
    }
}
package com.tlog.data.repository

import com.tlog.api.TravelApi
import com.tlog.data.model.travel.TravelDetailResponse
import javax.inject.Inject

class SearchOneDestinationRepository @Inject constructor(
    private val travelApi: TravelApi
) {
    suspend fun getDestinationById(id: String): TravelDetailResponse {
        return travelApi.getDestinationById(id)
    }
}
package com.tlog.data.repository

import com.tlog.api.TravelApi
import com.tlog.domain.mapper.toDomain
import com.tlog.domain.model.travel.TravelDetail
import javax.inject.Inject

class SearchOneDestinationRepository @Inject constructor(
    private val travelApi: TravelApi
) {
    suspend fun getDestinationById(id: String): TravelDetail {
        return travelApi.getDestinationById(id).data.toDomain()
    }
}

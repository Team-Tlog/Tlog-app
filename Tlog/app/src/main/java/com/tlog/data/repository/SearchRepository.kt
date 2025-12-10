package com.tlog.data.repository

import com.tlog.api.SearchApi
import com.tlog.domain.mapper.toDomain
import com.tlog.domain.model.travel.PopularTravel
import com.tlog.domain.model.travel.ViewTravel
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val retrofitInstance: SearchApi
) {
    suspend fun searchTravel(searchText: String): List<ViewTravel> {
        return retrofitInstance.searchTravelListByName(searchText).data.map {
            it.toDomain()
        }
    }

    suspend fun getPopularDestinations(): List<PopularTravel> {
        return retrofitInstance.getPopularDestinations().data.map {
            it.toDomain()
        }
    }
}

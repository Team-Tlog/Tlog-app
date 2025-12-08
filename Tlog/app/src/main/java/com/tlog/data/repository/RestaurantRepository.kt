package com.tlog.data.repository

import com.tlog.api.RestaurantApi
import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.data.dto.response.restaurant.RestaurantDto
import com.tlog.domain.mapper.toDomain
import com.tlog.domain.model.share.Restaurant
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
    private val retrofitInstance: RestaurantApi
) {
    suspend fun getEateryList(latitude: Double, longitude: Double): List<Restaurant> {
        return retrofitInstance.getEateryList(longitude, latitude).data.map {
            it.toDomain()
        }
    }

    suspend fun getCafeList(latitude: Double, longitude: Double): List<Restaurant> {
        return retrofitInstance.getCafeList(longitude, latitude).data.map {
            it.toDomain()
        }
    }
}

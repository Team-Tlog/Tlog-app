package com.tlog.data.repository

import com.tlog.api.RestaurantApi
import com.tlog.data.model.response.base.BaseResponse
import com.tlog.data.model.restaurant.Restaurant
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
    private val retrofitInstance: RestaurantApi
) {
    suspend fun getEateryList(latitude: Double, longitude: Double): BaseResponse<List<Restaurant>> {
        return retrofitInstance.getEateryList(longitude, latitude)
    }

    suspend fun getCafeList(latitude: Double, longitude: Double): BaseResponse<List<Restaurant>> {
        return retrofitInstance.getCafeList(longitude, latitude)
    }
}
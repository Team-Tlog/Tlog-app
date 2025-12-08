package com.tlog.api

import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.data.dto.response.restaurant.RestaurantDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantApi {
    @GET("/api/restaurant/eatery")
    suspend fun getEateryList(
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double
    ): BaseResponse<List<RestaurantDto>>

    @GET("/api/restaurant/cafe")
    suspend fun getCafeList(
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double
    ): BaseResponse<List<RestaurantDto>>
}
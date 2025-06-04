package com.tlog.api

import com.tlog.data.api.BaseResponse
import com.tlog.data.model.travel.ShopCart
import com.tlog.data.model.travel.Travel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.PUT
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.DELETE

interface UserApi {
    @GET("api/shopcart/user/{userId}")
    suspend fun getUserCart(
        @Path("userId") userId: String
    ): BaseResponse<List<ShopCart>>

    @PUT("api/shopcart/user/{userId}")
    @Headers("Content-Type: text/plain")
    suspend fun addDestinationToCart(
        @Path("userId") userId: String,
        @Body destinationId: okhttp3.RequestBody
    ): BaseResponse<Unit>

    @DELETE("api/shopcart/user/{userId}/destination/{destId}")
    suspend fun deleteTravelFromCart(
        @Path("userId") userId: String,
        @Path("destId") destinationId: String
    ): BaseResponse<Unit>
}
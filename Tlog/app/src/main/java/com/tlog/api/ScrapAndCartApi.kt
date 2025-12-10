package com.tlog.api

import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.data.dto.travel.CartDto
import com.tlog.data.dto.travel.ScrapDto
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Path

interface ScrapAndCartApi {
    @PUT("/api/scrap/user/{userId}")
    @Headers("Content-Type: text/plain")
    suspend fun scrapDestination(
        @Path("userId") userId: String,
        @Body travelId: RequestBody
    ): BaseResponse<Unit>

    @DELETE("/api/scrap/user/{userId}/destination/{destId}")
    suspend fun deleteScrapDestination(
        @Path("userId") userId: String,
        @Path("destId") destinationId: String
    ): BaseResponse<Unit>

    @GET("/api/scrap/user/{userId}")
    suspend fun getUserScraps(
        @Path("userId") userId: String
    ): BaseResponse<List<ScrapDto>>

    @GET("api/shopcart/user/{userId}")
    suspend fun getUserCart(
        @Path("userId") userId: String
    ): BaseResponse<List<CartDto>>

    @PUT("api/shopcart/user/{userId}")
    @Headers("Content-Type: text/plain")
    suspend fun addTravelToCart(
        @Path("userId") userId: String,
        @Body destinationId: okhttp3.RequestBody
    ): BaseResponse<Unit>

    @DELETE("api/shopcart/user/{userId}/destination/{destId}")
    suspend fun deleteTravelFromCart(
        @Path("userId") userId: String,
        @Path("destId") destinationId: String
    ): BaseResponse<Unit>
}

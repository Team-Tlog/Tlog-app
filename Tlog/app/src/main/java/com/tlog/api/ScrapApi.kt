package com.tlog.api

import com.tlog.data.api.BaseResponse
import com.tlog.data.api.ScrapDestinationResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Path

interface ScrapApi {
    @PUT("/api/scrap/user/{userId}")
    @Headers("Content-Type: text/plain")
    suspend fun scrapDestination(
        @Path("userId") userId: String,
        @Body travelId: okhttp3.RequestBody
    ): BaseResponse<Unit>

    @DELETE("/api/scrap/user/{userId}/destination/{destId}")
    suspend fun deleteScrapDestination(
        @Path("userId") userId: String,
        @Path("destId") destinationId: String
    ): BaseResponse<Unit>

    @GET("/api/scrap/user/{userId}")
    suspend fun getUserScraps(
        @Path("userId") userId: String
    ): BaseResponse<List<ScrapDestinationResponse>>
}
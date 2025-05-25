package com.tlog.api

import com.tlog.data.api.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("/api/search/destination")
    suspend fun getTravelList(
        @Query("searchText") searchText: String
    ): BaseResponse<List<MinimalTravel>>
}

data class MinimalTravel(
    val id: String,
    val name: String,
    val address: String
)
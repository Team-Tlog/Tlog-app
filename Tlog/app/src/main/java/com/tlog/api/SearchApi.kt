package com.tlog.api

import com.tlog.data.api.BaseResponse
import com.tlog.data.model.travel.SearchTravel
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("/api/search/destination/by-name") // 여행지 이름으로 검색
    suspend fun getTravelListByName(
        @Query("name") searchText: String
    ): BaseResponse<List<SearchTravel>>
}

data class MinimalTravel(
    val id: String,
    val name: String,
    val address: String
)
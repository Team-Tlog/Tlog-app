package com.tlog.api

import com.tlog.data.api.BaseResponse
import com.tlog.data.api.SearchAndPageResponse
import com.tlog.data.api.Sort
import com.tlog.data.model.travel.SearchTravel
import com.tlog.data.model.travel.TravelDestinationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("/api/search/destination/by-name") // 여행지 이름으로 검색
    suspend fun getTravelListByName(
        @Query("name") searchText: String
    ): BaseResponse<List<SearchTravel>>

    @GET("/api/search/destination/by-city") // 여행지 도시 이름으로 검색
    suspend fun getTravelListByCity(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: List<String>,
        @Query("city") city: String
    ): BaseResponse<SearchAndPageResponse>

    @GET("/api/search/destination/by-city-and-city") // 여행지 도시 이름 + 여행지 이름으로 검색
    suspend fun getTravelListByCityAndCity(
        @Query("pageable") pageable: Pageable,
        @Query("city") city: String,
        @Query("name") name: String
    ): BaseResponse<SearchAndPageResponse>

    @GET("/api/search/destination/by-address") // 여행지 주소로 검색
    suspend fun getTravelListByAddress(
        @Query("address") address: String
    ): BaseResponse<List<TravelDestinationResponse>>


}

data class Pageable(
    val pageNumber: Int,
    val pageSize: Int,
    val sort: Sort,
    val offset: Int,
    val sorted: Boolean,
    val unsorted: Boolean
)
package com.tlog.api

import com.tlog.data.api.BaseResponse
import com.tlog.data.api.Pageable
import com.tlog.data.api.SearchResponse
import com.tlog.data.api.SearchTravel
import com.tlog.data.api.TravelDestinationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("/api/search/destination/by-name") // 여행지 이름으로 검색
    suspend fun searchTravelListByName(
        @Query("name") searchText: String
    ): BaseResponse<List<SearchTravel>>

    @GET("/api/search/destination/by-city") // 여행지 도시 이름으로 검색
    suspend fun searchTravelListByCity(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: List<String>,
        @Query("city") city: String
    ): BaseResponse<SearchResponse>

    @GET("/api/search/destination/by-city-and-city") // 여행지 도시 이름 + 여행지 이름으로 검색
    suspend fun searchTravelListByCityAndCity(
        @Query("pageable") pageable: Pageable,
        @Query("city") city: String,
        @Query("name") name: String
    ): BaseResponse<SearchResponse>

    @GET("/api/search/destination/by-address") // 여행지 주소로 검색
    suspend fun searchTravelListByAddress(
        @Query("address") address: String
    ): BaseResponse<List<TravelDestinationResponse>>


}


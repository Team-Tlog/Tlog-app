package com.tlog.api

import com.tlog.data.model.response.base.BaseResponse
import com.tlog.data.model.response.page.Pageable
import com.tlog.data.model.response.travel.PopularDestination
import com.tlog.data.model.response.travel.SearchTravelResponse
import com.tlog.data.model.response.travel.TravelDestination
import com.tlog.data.model.response.travel.TravelSearch
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("/api/search/destination/by-name") // 여행지 이름으로 검색
    suspend fun searchTravelListByName(
        @Query("name") searchText: String
    ): BaseResponse<List<TravelSearch>>

    @GET("/api/search/destination/by-city") // 여행지 도시 이름으로 검색
    suspend fun searchTravelListByCity(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: List<String>,
        @Query("city") city: String
    ): BaseResponse<SearchTravelResponse>

    @GET("/api/search/destination/by-city-and-city") // 여행지 도시 이름 + 여행지 이름으로 검색
    suspend fun searchTravelListByCityAndCity(
        @Query("pageable") pageable: Pageable,
        @Query("city") city: String,
        @Query("name") name: String
    ): BaseResponse<SearchTravelResponse>

    @GET("/api/search/destination/by-address") // 여행지 주소로 검색
    suspend fun searchTravelListByAddress(
        @Query("address") address: String
    ): BaseResponse<List<SearchTravelResponse>>

    @GET("/api/search/destination/popular-destination") // 인기 여행지 조회
    suspend fun getPopularDestinations(): BaseResponse<List<PopularDestination>>

}


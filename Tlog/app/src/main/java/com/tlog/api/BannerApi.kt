package com.tlog.api

import com.tlog.data.api.BannerTravelResponse
import com.tlog.data.api.BaseListResponse
import com.tlog.data.api.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BannerApi {
    @GET("/api/recommend/destinations/{bannerId}")
    suspend fun getBanner(
        @Path("bannerId") bannerId: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<BannerTravelResponse>
}
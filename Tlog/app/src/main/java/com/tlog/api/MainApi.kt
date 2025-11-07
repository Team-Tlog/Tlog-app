package com.tlog.api

import com.tlog.data.api.BaseListPage
import com.tlog.data.api.BaseResponse
import com.tlog.data.model.share.LocalGuide
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApi {
    @GET("/api/local-guide")
    suspend fun getLocalGuide(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): BaseResponse<BaseListPage<List<LocalGuide>>>
}

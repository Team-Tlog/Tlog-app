package com.tlog.api

import com.tlog.data.api.BaseResponse
import com.tlog.data.api.TeamData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TeamApi {
    @GET("/api/team")
    suspend fun getTeamList(
        @Query("userId") userId: String
    ): Response<BaseResponse<List<TeamData>>>
}

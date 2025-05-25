package com.tlog.api

import com.tlog.data.api.BaseResponse
import com.tlog.data.api.TeamData
import com.tlog.data.api.CreateTeamRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TeamApi {
    @GET("/api/team")
    suspend fun getTeamList(
        @Query("userId") userId: String
    ): BaseResponse<List<TeamData>>

    @POST("/api/team")
    suspend fun createTeam(
        @Body request: CreateTeamRequest
    ): BaseResponse<String>

    @DELETE("/api/team/{teamId}")
    suspend fun deleteTeam(
        @Path("teamId") teamId: String
    ): BaseResponse<String>
}

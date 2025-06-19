package com.tlog.api

import com.tlog.data.api.BaseResponse
import com.tlog.data.api.CreateTeamRequest
import com.tlog.data.api.JoinTeamRequest
import com.tlog.data.api.TeamCreateResponse
import com.tlog.data.model.team.DetailTeam
import com.tlog.data.model.team.Team
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
    ): BaseResponse<List<Team>>

    @POST("/api/team")
    suspend fun createTeam(
        @Body request: CreateTeamRequest
    ): BaseResponse<TeamCreateResponse>

    @DELETE("/api/team/{teamId}")
    suspend fun deleteTeam(
        @Path("teamId") teamId: String
    ): BaseResponse<String>

    @GET("/api/team/{teamId}/details")
    suspend fun getTeamDetails(
        @Path("teamId") teamId: String
    ): BaseResponse<DetailTeam>

    @POST("/api/team/join")
    suspend fun joinTeam(
        @Body request: JoinTeamRequest
    ): BaseResponse<Unit>
}

package com.tlog.data.repository

import android.util.Log
import com.tlog.api.TeamApi
import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.data.dto.request.team.CreateTeamRequest
import com.tlog.data.dto.request.team.JoinTeamRequest
import com.tlog.data.dto.response.team.TeamCreateResponse
import com.tlog.data.dto.team.TeamDetailDto
import com.tlog.data.dto.team.TeamDto
import jakarta.inject.Inject

class TeamRepository @Inject constructor(
    private val retrofitInstance: TeamApi
) {
    suspend fun getTeamList(userId: String): BaseResponse<List<TeamDto>>{
        val result = retrofitInstance.getTeamList(userId)
        Log.d("MyTeamListRepository", "addReview: $result")
        return result
    }

    suspend fun getTeamDetails(teamId: String): BaseResponse<TeamDetailDto> {
        return retrofitInstance.getTeamDetails(teamId)
    }

    suspend fun joinTeam(teamCode: String, userId: String): BaseResponse<Unit> {
        return retrofitInstance.joinTeam(JoinTeamRequest(inviteCode = teamCode, userId = userId))
    }

    suspend fun createTeam(request: CreateTeamRequest): BaseResponse<TeamCreateResponse>{
        val result = retrofitInstance.createTeam(request)
        return result
    }

    suspend fun deleteTeam(teamId: String): BaseResponse<String> {
        val result = retrofitInstance.deleteTeam(teamId)
        return result
    }

    suspend fun leaveTeam(teamId: String, userId: String): BaseResponse<String> {
        val result = retrofitInstance.leaveTeam(teamId, userId)
        return result
    }
}

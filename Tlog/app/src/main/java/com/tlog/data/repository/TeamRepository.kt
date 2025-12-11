package com.tlog.data.repository

import com.tlog.api.TeamApi
import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.data.dto.request.team.CreateTeamRequest
import com.tlog.data.dto.request.team.JoinTeamRequest
import com.tlog.domain.mapper.toDomain
import com.tlog.domain.model.team.Team
import com.tlog.domain.model.team.TeamDetail
import jakarta.inject.Inject

class TeamRepository @Inject constructor(
    private val retrofitInstance: TeamApi
) {
    suspend fun getTeamList(userId: String): List<Team> {
        return retrofitInstance.getTeamList(userId).data.map {
            it.toDomain()
        }
    }

    suspend fun getTeamDetails(teamId: String): TeamDetail {
        return retrofitInstance.getTeamDetails(teamId).data.toDomain()
    }

    suspend fun joinTeam(teamCode: String, userId: String): BaseResponse<Unit> {
        return retrofitInstance.joinTeam(JoinTeamRequest(inviteCode = teamCode, userId = userId))
    }

    suspend fun createTeam(request: CreateTeamRequest) {
        retrofitInstance.createTeam(request)
    }

    suspend fun deleteTeam(teamId: String) {
        retrofitInstance.deleteTeam(teamId)
    }

    suspend fun leaveTeam(teamId: String, userId: String) {
        retrofitInstance.leaveTeam(teamId, userId)
    }
}

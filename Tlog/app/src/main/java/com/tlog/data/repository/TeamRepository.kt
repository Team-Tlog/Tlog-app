package com.tlog.data.repository

import android.util.Log
import com.tlog.api.TeamApi
import com.tlog.api.TeamCreateResponse
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.CreateTeamRequest
import com.tlog.data.api.JoinTeamRequest
import com.tlog.data.api.TeamData
import com.tlog.data.api.TeamDetailData
import jakarta.inject.Inject

class TeamRepository @Inject constructor(
    private val retrofitInstance: TeamApi
) {
    suspend fun getTeamList(userId: String): BaseResponse<List<TeamData>>{
        val result = retrofitInstance.getTeamList(userId)
        Log.d("MyTeamListRepository", "addReview: $result")
        return result
    }

    suspend fun getTeamDetails(teamId: String): BaseResponse<TeamDetailData> {
        return retrofitInstance.getTeamDetails(teamId)
    }

    suspend fun joinTeam(teamCode: String, userId: String): BaseResponse<Unit> {
        return retrofitInstance.joinTeam(JoinTeamRequest(inviteCode = teamCode, userId = userId))
    }

    suspend fun createTeam(request: CreateTeamRequest): BaseResponse<TeamCreateResponse>{
        val result = retrofitInstance.createTeam(request)
        Log.d("ReviewRepository", "addReview: $result")
        return result
    }

    suspend fun deleteTeam(teamId: String): BaseResponse<String> {
        val result = retrofitInstance.deleteTeam(teamId)
        Log.d("TeamDeleteRepository", "deleteTeam: $result")
        return result
    }
}


package com.tlog.data.repository

import android.util.Log
import com.tlog.api.TeamApi
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.CreateTeamRequest
import jakarta.inject.Inject

class TeamNameRepository @Inject constructor(
    private val retrofitInstance: TeamApi
) {
    suspend fun createTeam(request: CreateTeamRequest): BaseResponse<String>{
        val result = retrofitInstance.createTeam(request)
        Log.d("ReviewRepository", "addReview: $result")
        return result
    }
}
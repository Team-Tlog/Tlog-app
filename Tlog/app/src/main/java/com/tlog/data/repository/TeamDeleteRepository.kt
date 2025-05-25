package com.tlog.data.repository

import android.util.Log
import com.tlog.api.TeamApi
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.TeamData
import jakarta.inject.Inject

class TeamDeleteRepository @Inject constructor(
    private val retrofitInstance: TeamApi
) {
    suspend fun deleteTeam(teamId: String): BaseResponse<String> {
        val result = retrofitInstance.deleteTeam(teamId)
        Log.d("TeamDeleteRepository", "deleteTeam: $result")
        return result
    }
}

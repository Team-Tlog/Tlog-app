package com.tlog.data.repository

import android.util.Log
import com.tlog.api.TeamApi
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.TeamData
import jakarta.inject.Inject

class MyTeamListRepository @Inject constructor(
    private val retrofitInstance: TeamApi
) {
    suspend fun getTeamList(userId: String): BaseResponse<List<TeamData>>{
        val result = retrofitInstance.getTeamList(userId)
        Log.d("MyTeamListRepository", "addReview: $result")
        return result
    }
}
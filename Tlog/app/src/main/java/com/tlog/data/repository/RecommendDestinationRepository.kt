package com.tlog.data.repository

import android.util.Log
import com.kakao.sdk.friend.core.l.T
import com.tlog.api.TeamApi
import com.tlog.api.TravelApi
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.TeamData
import com.tlog.data.model.travel.TravelDestinationResponse
import com.tlog.data.model.travel.TravelRecommendPagedResponse
import jakarta.inject.Inject

class RecommendDestinationRepository @Inject constructor(
    private val retrofitInstance: TravelApi
) {
    suspend fun getDestinations(): TravelRecommendPagedResponse<TravelDestinationResponse>{
        val result = retrofitInstance.getDestinations(
            page = 0,
            size = 5,
            sort = "rating,desc"
        )
        Log.d("RecommendDestinationRepository", "getDestination: $result")
        return result
    }
}
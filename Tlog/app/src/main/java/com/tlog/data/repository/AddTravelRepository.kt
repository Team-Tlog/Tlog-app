package com.tlog.data.repository

import com.tlog.api.TravelApi
import com.tlog.data.api.AddTravelRequest
import com.tlog.data.api.BaseResponse
import jakarta.inject.Inject

class AddTravelRepository @Inject constructor(
    private val retrofitInstance: TravelApi
){

    suspend fun addTravel(travel: AddTravelRequest): BaseResponse<String?> {
        return retrofitInstance.addTravel(travel)

    }
}
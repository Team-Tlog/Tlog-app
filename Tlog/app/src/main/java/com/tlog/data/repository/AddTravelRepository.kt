package com.tlog.data.repository

import com.tlog.api.TravelApi
import com.tlog.data.dto.request.travel.AddTravelRequest
import jakarta.inject.Inject

class AddTravelRepository @Inject constructor(
    private val retrofitInstance: TravelApi
){
    suspend fun addTravel(travel: AddTravelRequest) {
        retrofitInstance.addTravel(travel)
    }
}

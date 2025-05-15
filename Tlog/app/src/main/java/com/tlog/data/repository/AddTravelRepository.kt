package com.tlog.data.repository

import android.util.Log
import com.tlog.api.RetrofitInstance
import com.tlog.api.TravelApi
import com.tlog.data.model.travel.Travel
import jakarta.inject.Inject
import retrofit2.create

class AddTravelRepository @Inject constructor(
    private val retrofitInstance: TravelApi
){

    suspend fun addTravel(travel: Travel) {
        val result = retrofitInstance.addTravel(travel).data
        //retrofitInstance.addTravel(travel)
    }
}
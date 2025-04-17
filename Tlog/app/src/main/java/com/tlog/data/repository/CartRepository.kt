package com.tlog.data.repository

import android.util.Log
import com.tlog.api.RetrofitInstance
import com.tlog.api.UserApi
import com.tlog.ui.api.travel.TravelData
import retrofit2.create

class CartRepository {
    private val retrofitInstance: UserApi = RetrofitInstance.getInstance().create()

    suspend fun getUserCart(userId: String): List<TravelData> {
        val result = retrofitInstance.getUserCart(userId).data
        Log.d("http log", result.toString())
        return result
    }
}
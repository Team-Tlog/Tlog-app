package com.tlog.data.repository

import android.util.Log
import com.tlog.api.RetrofitInstance
import com.tlog.api.UserApi
import com.tlog.data.model.travel.Travel
import retrofit2.create

class CartRepository {
    private val retrofitInstance: UserApi = RetrofitInstance.getInstance().create()

    suspend fun getUserCart(userId: String): List<Travel> {
        val result = retrofitInstance.getUserCart(userId).data
        Log.d("http log", result.toString())
        return result
    }
}
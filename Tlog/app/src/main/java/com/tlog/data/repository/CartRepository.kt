package com.tlog.data.repository

import android.util.Log
import com.tlog.api.RetrofitInstance
import com.tlog.api.UserApi
import com.tlog.ui.api.travel.TravelData

class CartRepository {
    var retrofitInstance: UserApi = RetrofitInstance.getInstance().create(UserApi::class.java)

    suspend fun getUserCart(userId: String): List<TravelData> {
        val result = retrofitInstance.getUserCart(userId).data
        Log.d("http log", result.toString())
        return result
    }
}
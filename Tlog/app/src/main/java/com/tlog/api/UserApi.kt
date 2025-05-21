package com.tlog.api

import com.tlog.data.api.BaseResponse
import com.tlog.data.model.travel.Travel
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET("api/shopcart/user/{userId}")
    suspend fun getUserCart(
        @Path("userId") userId: String
    ): BaseResponse<List<Travel>>

}
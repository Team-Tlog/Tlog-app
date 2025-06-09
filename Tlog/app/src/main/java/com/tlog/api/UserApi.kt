package com.tlog.api

import com.tlog.data.api.BaseResponse
import com.tlog.data.model.travel.ShopCart
import com.tlog.data.model.travel.Travel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.PUT
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.DELETE

interface UserApi {
    @GET("api/shopcart/user/{userId}")
    suspend fun getUserCart(
        @Path("userId") userId: String
    ): BaseResponse<List<ShopCart>>

    @PUT("api/shopcart/user/{userId}")
    @Headers("Content-Type: text/plain")
    suspend fun addDestinationToCart(
        @Path("userId") userId: String,
        @Body destinationId: okhttp3.RequestBody
    ): BaseResponse<Unit>

    @DELETE("api/shopcart/user/{userId}/destination/{destId}")
    suspend fun deleteTravelFromCart(
        @Path("userId") userId: String,
        @Path("destId") destinationId: String
    ): BaseResponse<Unit>

    // 기본 마이페이지 조회 (SNS 아님)
    @GET("api/users/my-page")
    suspend fun getUserInfo(): BaseResponse<UserInfo>
}

data class UserInfo(
    val username: String,
    val snsId: String,
    val profileImageUrl: String?,
    val defaultRewardPhrase: String,
    val userRewards: List<Reward>,
    val tbtiDescription: Tbti
)

data class Reward(
    val rewardId: String,
    val name: String,
    val description: String,
    val iconImageUrl: String,
    val isDefaultReward: Boolean
)

data class Tbti(
    val tbtiString: String,
    val imageUrl: String?,
    val secondName: String,
    val description: String
)
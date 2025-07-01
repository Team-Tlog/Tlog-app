package com.tlog.api

import com.tlog.data.api.BaseResponse
import com.tlog.data.api.ProfileImageRequest
import com.tlog.data.model.travel.Cart
import com.tlog.data.model.user.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.PUT
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.DELETE
import retrofit2.http.POST

interface UserApi {
    @GET("api/shopcart/user/{userId}")
    suspend fun getUserCart(
        @Path("userId") userId: String
    ): BaseResponse<List<Cart>>

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
    suspend fun getUserInfo(): BaseResponse<User>

    //마이페이지에서 프로필사진 업로드
    @POST("/api/users/profile-image")
    suspend fun updateProfileImage(
        @Body request: ProfileImageRequest
    ): BaseResponse<String>
}



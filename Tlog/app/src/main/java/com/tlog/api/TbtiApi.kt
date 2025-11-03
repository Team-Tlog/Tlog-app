package com.tlog.api

import com.tlog.data.api.BaseResponse
import com.tlog.data.api.UpdateTbtiResponse
import com.tlog.data.model.share.TbtiDescription
import com.tlog.data.model.tbti.TbtiQuestion
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TbtiApi {
    @GET("/api/tbti/user/questions")
    suspend fun getTbtiQuestions(
        @Query("categories") categories: String
    ): BaseResponse<List<TbtiQuestion>>

    @GET("/api/tbti-info")
    suspend fun getTbtiDescription(
        @Query("tbti") tbti: String
    ): BaseResponse<TbtiDescription>

    @POST("/api/users/tbti")
    suspend fun updateTbti(
        @Query("tbti") tbti: Int
    ): BaseResponse<UpdateTbtiResponse>
}


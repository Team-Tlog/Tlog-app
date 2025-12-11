package com.tlog.api

import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.data.dto.response.tbti.UpdateTbtiResponse
import com.tlog.data.dto.tbti.TbtiDescriptionDto
import com.tlog.data.dto.tbti.TbtiQuestionDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TbtiApi {
    @GET("/api/tbti/user/questions")
    suspend fun getTbtiQuestions(
        @Query("categories") categories: String
    ): BaseResponse<List<TbtiQuestionDto>>

    @GET("/api/tbti-info")
    suspend fun getTbtiDescription(
        @Query("tbti") tbti: String
    ): BaseResponse<TbtiDescriptionDto>

    @POST("/api/users/tbti")
    suspend fun updateTbti(
        @Query("tbti") tbti: Int
    ): BaseResponse<UpdateTbtiResponse>
}


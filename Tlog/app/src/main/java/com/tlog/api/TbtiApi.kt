package com.tlog.api

import com.tlog.data.api.BaseResponse
import com.tlog.data.api.TbtiDescriptionResponse
import com.tlog.data.api.TbtiQuestionItem
import retrofit2.http.GET
import retrofit2.http.Query

interface TbtiApi {
    @GET("/api/tbti/user/questions")
    suspend fun getTbtiQuestions(
        @Query("categories") categories: String
    ): BaseResponse<List<TbtiQuestionItem>>

    @GET("/api/tbti-info")
    suspend fun getTbtiDescription(
        @Query("tbti") tbti: String
    ): BaseResponse<TbtiDescriptionResponse>
}
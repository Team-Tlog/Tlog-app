package com.tlog.data.repository

import com.tlog.api.TbtiApi
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.TbtiDescriptionResponse
import com.tlog.data.api.TbtiQuestionItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TbtiRepository @Inject constructor(
    private val tbtiApi: TbtiApi
) {
    suspend fun getTbtiQuestions(categories: String? = null): BaseResponse<List<TbtiQuestionItem>> {
        return withContext(Dispatchers.IO) {
            tbtiApi.getTbtiQuestions(categories.toString())
        }
    }
    
    suspend fun getTbtiDescription(tbti: String): BaseResponse<TbtiDescriptionResponse> {
        return withContext(Dispatchers.IO) {
            tbtiApi.getTbtiDescription(tbti)
        }
    }

}
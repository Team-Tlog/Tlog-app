package com.tlog.data.repository

import com.tlog.api.TbtiApi
import com.tlog.api.UpdateTbtiResponse
import com.tlog.data.api.BaseResponse
import com.tlog.data.api.TbtiDescriptionResponse
import com.tlog.data.api.TbtiQuestionItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TbtiRepository @Inject constructor(
    private val retrofitInstance: TbtiApi
) {
    suspend fun getTbtiQuestions(categories: String? = null): BaseResponse<List<TbtiQuestionItem>> {
        return withContext(Dispatchers.IO) {
            retrofitInstance.getTbtiQuestions(categories.toString())
        }
    }
    
    suspend fun getTbtiDescription(tbti: String): BaseResponse<TbtiDescriptionResponse> {
        return withContext(Dispatchers.IO) {
            retrofitInstance.getTbtiDescription(tbti)
        }
    }

    suspend fun updateTbti(tbtiValue: String): BaseResponse<UpdateTbtiResponse> {
        return retrofitInstance.updateTbti(tbtiValue.toInt())
    }

}
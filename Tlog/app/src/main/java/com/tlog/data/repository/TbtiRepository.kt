package com.tlog.data.repository

import com.tlog.api.TbtiApi
import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.data.dto.response.tbti.UpdateTbtiResponse
import com.tlog.data.dto.tbti.TbtiDescriptionDto
import com.tlog.data.dto.tbti.TbtiQuestionDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TbtiRepository @Inject constructor(
    private val retrofitInstance: TbtiApi
) {
    suspend fun getTbtiQuestions(categories: String? = null): BaseResponse<List<TbtiQuestionDto>> {
        return withContext(Dispatchers.IO) {
            retrofitInstance.getTbtiQuestions(categories.toString())
        }
    }
    
    suspend fun getTbtiDescription(tbti: String): BaseResponse<TbtiDescriptionDto> {
        return withContext(Dispatchers.IO) {
            retrofitInstance.getTbtiDescription(tbti)
        }
    }

    suspend fun updateTbti(tbtiValue: String): BaseResponse<UpdateTbtiResponse> {
        return retrofitInstance.updateTbti(tbtiValue.toInt())
    }
}

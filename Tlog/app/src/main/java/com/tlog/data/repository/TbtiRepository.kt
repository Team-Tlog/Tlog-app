package com.tlog.data.repository

import com.tlog.api.TbtiApi
import com.tlog.domain.mapper.toDomain
import com.tlog.domain.model.tbti.TbtiDescription
import com.tlog.domain.model.tbti.TbtiQuestion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TbtiRepository @Inject constructor(
    private val retrofitInstance: TbtiApi
) {
    suspend fun getTbtiQuestions(categories: String? = null): List<TbtiQuestion> {
        return withContext(Dispatchers.IO) {
            retrofitInstance.getTbtiQuestions(categories.toString()).data.map {
                it.toDomain()
            }
        }
    }
    
    suspend fun getTbtiDescription(tbti: String): TbtiDescription {
        return retrofitInstance.getTbtiDescription(tbti).data.toDomain()
    }

    suspend fun updateTbti(tbtiValue: String) {
        retrofitInstance.updateTbti(tbtiValue.toInt())
    }
}

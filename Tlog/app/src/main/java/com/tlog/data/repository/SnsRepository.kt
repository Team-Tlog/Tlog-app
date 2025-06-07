package com.tlog.data.repository

import com.tlog.api.SnsApi
import com.tlog.api.UpdateSnsIdRequest
import com.tlog.data.api.BaseResponse
import javax.inject.Inject

class SnsRepository @Inject constructor(
    private val retrofitInstance: SnsApi,
) {
    suspend fun updateSnsId(snsId: String): BaseResponse<Unit> {
        return retrofitInstance.updateSnsId(UpdateSnsIdRequest(snsId))
    }
}
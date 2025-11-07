package com.tlog.data.repository

import com.tlog.api.MainApi
import com.tlog.data.api.BaseListPage
import com.tlog.data.api.BaseResponse
import com.tlog.data.model.share.LocalGuide
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val retrofitInstance: MainApi
) {
    suspend fun getLocalGuide(
        latitude: Double,
        longitude: Double
    ): BaseResponse<BaseListPage<List<LocalGuide>>> {
        return retrofitInstance.getLocalGuide(latitude, longitude)
    }
}

package com.tlog.data.repository

import com.tlog.api.BannerApi
import com.tlog.data.api.BaseListResponse
import com.tlog.data.api.TravelDestinationResponse
import jakarta.inject.Inject


class BannerRepository @Inject constructor(
    private val retrofitInstance: BannerApi
) {
    suspend fun getBannerDetail(
        bannerId: String,
        page: Int = 0,
        size: Int = 10
    ): BaseListResponse<List<TravelDestinationResponse>> {
        return retrofitInstance.getBanner(bannerId, page, size)
    }
}
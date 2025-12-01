package com.tlog.data.repository

import com.tlog.api.BannerApi
import com.tlog.data.model.response.travel.BannerTravelResponse
import com.tlog.data.model.response.base.BaseResponse
import jakarta.inject.Inject


class BannerRepository @Inject constructor(
    private val retrofitInstance: BannerApi
) {
    suspend fun getBannerDetail(
        bannerId: String,
        page: Int = 0,
        size: Int = 10,
    ): BaseResponse<BannerTravelResponse> {
        return retrofitInstance.getBanner(bannerId, page, size)
    }
}

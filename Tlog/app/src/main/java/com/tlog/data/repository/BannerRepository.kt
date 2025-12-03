package com.tlog.data.repository

import com.tlog.api.BannerApi
import com.tlog.data.dto.response.travel.BannerTravelResponse
import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.domain.mapper.toDomain
import com.tlog.domain.model.travel.BannerDetail
import com.tlog.domain.model.travel.Travel
import jakarta.inject.Inject


class BannerRepository @Inject constructor(
    private val retrofitInstance: BannerApi
) {
    suspend fun getBannerDetail(
        bannerId: String,
        page: Int = 0,
        size: Int = 10,
    ): BannerDetail {
        return retrofitInstance.getBanner(bannerId, page, size).data.toDomain()
    }
}

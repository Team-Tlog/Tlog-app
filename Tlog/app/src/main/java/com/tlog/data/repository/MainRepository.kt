package com.tlog.data.repository

import com.tlog.api.MainApi
import com.tlog.data.dto.response.base.BaseListPage
import com.tlog.data.dto.response.base.BaseListResponse
import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.data.dto.share.BannerDto
import com.tlog.data.dto.share.BannerDetailDto
import com.tlog.data.dto.share.LocalGuideDto
import com.tlog.data.dto.share.PostDto
import com.tlog.data.dto.share.RecommendDestinationDto
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val retrofitInstance: MainApi
) {
    suspend fun getLocalGuide(
        latitude: Double,
        longitude: Double
    ): BaseResponse<BaseListPage<List<LocalGuideDto>>> {
        return retrofitInstance.getLocalGuide(latitude, longitude)
    }

    suspend fun getRecommendPost(): BaseResponse<List<PostDto>> {
        return retrofitInstance.getRecommendPost()
    }

    suspend fun getRecommendDestination(): BaseResponse<List<RecommendDestinationDto>> {
        return retrofitInstance.getRecommendDestination()
    }

    suspend fun getRecommendBanner(): BaseResponse<List<BannerDto>> {
        return retrofitInstance.getRecommendBanner()
    }

    suspend fun getRecommendBannerDetail(
        bannerId: String,
        page: Int = 0,
        size: Int = 10
    ): BaseListResponse<List<BannerDetailDto>> {
        return retrofitInstance.getRecommendBannerDetail(bannerId, page, size)
    }
}

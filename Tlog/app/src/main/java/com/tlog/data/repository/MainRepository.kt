package com.tlog.data.repository

import com.tlog.api.MainApi
import com.tlog.data.api.BaseListPage
import com.tlog.data.api.BaseListResponse
import com.tlog.data.api.BaseResponse
import com.tlog.data.model.share.Banner
import com.tlog.data.model.share.BannerItem
import com.tlog.data.model.share.LocalGuide
import com.tlog.data.model.share.Post
import com.tlog.data.model.share.RecommendDestination
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

    suspend fun getRecommendPost(): BaseResponse<List<Post>> {
        return retrofitInstance.getRecommendPost()
    }

    suspend fun getRecommendDestination(): BaseResponse<List<RecommendDestination>> {
        return retrofitInstance.getRecommendDestination()
    }

    suspend fun getRecommendBanner(): BaseResponse<List<Banner>> {
        return retrofitInstance.getRecommendBanner()
    }

    suspend fun getRecommendBannerDetail(
        bannerId: String,
        page: Int = 0,
        size: Int = 10
    ): BaseListResponse<List<BannerItem>> {
        return retrofitInstance.getRecommendBannerDetail(bannerId, page, size)
    }
}

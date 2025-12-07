package com.tlog.data.repository

import com.tlog.api.MainApi
import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.data.dto.share.BannerDto
import com.tlog.data.dto.share.PostDto
import com.tlog.data.dto.share.RecommendDestinationDto
import com.tlog.domain.mapper.toDomain
import com.tlog.domain.model.share.LocalGuide
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val retrofitInstance: MainApi
) {
    suspend fun getLocalGuide(
        latitude: Double,
        longitude: Double
    ): List<LocalGuide> {
        return retrofitInstance.getLocalGuide(latitude, longitude).data.content.map {
            it.toDomain()
        }
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
}

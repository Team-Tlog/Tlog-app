package com.tlog.data.repository

import com.tlog.api.MainApi
import com.tlog.domain.mapper.toDomain
import com.tlog.domain.model.share.Banner
import com.tlog.domain.model.share.LocalGuide
import com.tlog.domain.model.share.RecommendPost
import com.tlog.domain.model.share.RecommendTravels
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

    suspend fun getRecommendPost(): List<RecommendPost> {
        return retrofitInstance.getRecommendPost().data.map {
            it.toDomain()
        }
    }

    suspend fun getRecommendDestination(): List<RecommendTravels> {
        return retrofitInstance.getRecommendDestination().data.map {
            it.toDomain()
        }
    }

    suspend fun getRecommendBanner(): List<Banner> {
        return retrofitInstance.getRecommendBanner().data.map {
            it.toDomain()
        }
    }
}

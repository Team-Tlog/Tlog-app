package com.tlog.api

import com.tlog.data.dto.response.base.BaseListPage
import com.tlog.data.dto.response.base.BaseListResponse
import com.tlog.data.dto.response.base.BaseResponse
import com.tlog.data.dto.share.BannerDto
import com.tlog.data.dto.share.BannerDetailDto
import com.tlog.data.dto.share.LocalGuideDto
import com.tlog.data.dto.share.PostDto
import com.tlog.data.dto.share.RecommendDestinationDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {
    @GET("/api/local-guide")
    suspend fun getLocalGuide(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): BaseResponse<BaseListPage<List<LocalGuideDto>>>

    @GET("/api/recommend/posts")
    suspend fun getRecommendPost(): BaseResponse<List<PostDto>>

    @GET("/api/recommend/destinations")
    suspend fun getRecommendDestination(): BaseResponse<List<RecommendDestinationDto>>

    @GET("/api/recommend/banners")
    suspend fun getRecommendBanner(): BaseResponse<List<BannerDto>>

    @GET("/api/recomment/destinations/{bannerId}")
    suspend fun getRecommendBannerDetail(
        @Path("bannerId") bannerId: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10
    ): BaseListResponse<List<BannerDetailDto>>
}

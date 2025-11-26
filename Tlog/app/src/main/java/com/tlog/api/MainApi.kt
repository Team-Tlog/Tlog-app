package com.tlog.api

import com.tlog.data.model.response.base.BaseListPage
import com.tlog.data.model.response.base.BaseListResponse
import com.tlog.data.model.response.base.BaseResponse
import com.tlog.data.model.share.Banner
import com.tlog.data.model.share.BannerItem
import com.tlog.data.model.share.LocalGuide
import com.tlog.data.model.share.Post
import com.tlog.data.model.share.RecommendDestination
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {
    @GET("/api/local-guide")
    suspend fun getLocalGuide(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): BaseResponse<BaseListPage<List<LocalGuide>>>

    @GET("/api/recommend/posts")
    suspend fun getRecommendPost(): BaseResponse<List<Post>>

    @GET("/api/recommend/destinations")
    suspend fun getRecommendDestination(): BaseResponse<List<RecommendDestination>>

    @GET("/api/recommend/banners")
    suspend fun getRecommendBanner(): BaseResponse<List<Banner>>

    @GET("/api/recomment/destinations/{bannerId}")
    suspend fun getRecommendBannerDetail(
        @Path("bannerId") bannerId: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10
    ): BaseListResponse<List<BannerItem>>
}

package com.tlog.di

import com.tlog.api.AiApi
import com.tlog.api.BannerApi
import com.tlog.api.LoginApi
import com.tlog.api.MainApi
import com.tlog.api.RestaurantApi
import com.tlog.api.SearchApi
import com.tlog.api.SnsApi
import com.tlog.api.TbtiApi
import com.tlog.api.TeamApi
import com.tlog.api.TravelApi
import com.tlog.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideMainApi(
        retrofit: Retrofit
    ): MainApi {
        return retrofit.create(MainApi::class.java)
    }

    @Provides
    fun provideRestaurantApi(
        retrofit: Retrofit
    ): RestaurantApi {
        return retrofit.create(RestaurantApi::class.java)
    }

    @Provides
    fun provideBannerApi(
        retrofit: Retrofit
    ): BannerApi {
        return retrofit.create(BannerApi::class.java)
    }

    @Provides
    fun provideSearchApi(
        retrofit: Retrofit
    ): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }

    @Provides
    fun provideSnsApi(
        retrofit: Retrofit
    ): SnsApi {
        return retrofit.create(SnsApi::class.java)
    }

    @Provides
    fun provideTeamApi(
        retrofit: Retrofit
    ): TeamApi {
        return retrofit.create(TeamApi::class.java)
    }

    @Provides
    fun provideTravelApi(
        retrofit: Retrofit
    ): TravelApi {
        return retrofit.create(TravelApi::class.java)
    }

    @Provides
    fun provideAiApi(
        retrofit: Retrofit
    ): AiApi {
        return retrofit.create(AiApi::class.java)
    }

    @Provides
    fun provideUserApi(
        retrofit: Retrofit
    ): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    fun provideLoginApi(
        retrofit: Retrofit
    ): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Provides
    fun provideTbtiApi(
        retrofit: Retrofit
    ): TbtiApi {
        return retrofit.create(TbtiApi::class.java)
    }
}
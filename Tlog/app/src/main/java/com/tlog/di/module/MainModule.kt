package com.tlog.di.module

import com.tlog.api.MainApi
import com.tlog.api.RestaurantApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
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
}
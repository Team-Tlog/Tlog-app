package com.tlog.di.module

import com.tlog.api.TravelApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object TravelModule {
    @Provides
    fun provideTravelApi(
        retrofit: Retrofit
    ): TravelApi {
        return retrofit.create(TravelApi::class.java)
    }

}
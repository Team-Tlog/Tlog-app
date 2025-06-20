package com.tlog.di.module

import com.tlog.api.SnsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object SnsModule {
    @Provides
    fun provideSnsApi(
        retrofit: Retrofit
    ): SnsApi {
        return retrofit.create(SnsApi::class.java)
    }
}
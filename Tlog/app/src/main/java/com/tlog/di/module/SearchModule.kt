package com.tlog.di.module

import com.tlog.api.SearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {
    @Provides
    fun provideSearchApi(
        retrofit: Retrofit
    ): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }
}
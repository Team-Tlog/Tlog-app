package com.tlog.di.module

import com.tlog.api.TeamApi
import com.tlog.data.repository.TeamRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
object TeamModule {
    @Provides
    fun provideTeamApi(
        retrofit: Retrofit
    ): TeamApi {
        return retrofit.create(TeamApi::class.java)
    }
}
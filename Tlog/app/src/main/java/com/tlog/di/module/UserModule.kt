package com.tlog.di.module

import com.tlog.api.LoginApi
import com.tlog.api.TbtiApi
import com.tlog.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
object UserModule {
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
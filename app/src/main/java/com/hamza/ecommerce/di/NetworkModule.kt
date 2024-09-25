package com.hamza.ecommerce.di

import com.hamza.ecommerce.data.datasource.networking.CloudFunctionAPI
import com.hamza.ecommerce.data.datasource.networking.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideCloudFunctionAPI(): CloudFunctionAPI {
        return RetrofitInstance.api
    }
}
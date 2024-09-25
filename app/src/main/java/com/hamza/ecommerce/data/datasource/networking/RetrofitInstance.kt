package com.hamza.ecommerce.data.datasource.networking

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private const val BASE_URL = "https://us-central1-android-e-commerce-training.cloudfunctions.net/"
    private const val TIME_OUT = 60L

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { ErrorLoggingInterceptor().intercept(it) }
            .build()
    }

    val api: CloudFunctionAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(CloudFunctionAPI::class.java)
    }
}
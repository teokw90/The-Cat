package com.example.bestandroidcode.di

import com.example.bestandroidcode.BuildConfig
import com.example.bestandroidcode.source.remote.TheCatWebService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Kuan Wah Teo on 07/11/2020
 **/
@Module
class WebServiceModule {
    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(2000L, TimeUnit.MILLISECONDS)
        .writeTimeout(2000L, TimeUnit.MILLISECONDS)
        .connectTimeout(2000L, TimeUnit.MILLISECONDS)
        .retryOnConnectionFailure(false)
        .build()

    @Provides
    @Singleton
    fun provideTheCatWebService(): TheCatWebService = Retrofit.Builder()
        .baseUrl("${BuildConfig.BaseURL}")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(TheCatWebService::class.java)
}
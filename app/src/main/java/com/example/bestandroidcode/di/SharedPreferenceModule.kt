package com.example.bestandroidcode.di

import android.content.Context
import android.content.SharedPreferences
import com.example.bestandroidcode.BuildConfig
import com.example.bestandroidcode.MainApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Kuan Wah Teo on 10/11/2020
 **/

@Module
class SharedPreferenceModule {
    @Provides
    @Singleton
    fun provideSharedPreference(application: MainApplication): SharedPreferences
        = application.getSharedPreferences(BuildConfig.SharedPreferencesName, Context.MODE_PRIVATE)
}
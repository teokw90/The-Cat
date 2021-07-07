package com.example.bestandroidcode.di

import com.example.bestandroidcode.view.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Kuan Wah Teo on 07/11/2020
 **/

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeSearchActivity(): MainActivity
}
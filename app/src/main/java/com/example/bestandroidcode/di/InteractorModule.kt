package com.example.bestandroidcode.di

import com.example.bestandroidcode.interactor.DefaultMyFavoriteInteractor
import com.example.bestandroidcode.interactor.DefaultTheCatInteractor
import com.example.bestandroidcode.interactor.MyFavoriteInteractor
import com.example.bestandroidcode.interactor.TheCatInteractor
import com.example.bestandroidcode.source.MyFavoriteRepository
import com.example.bestandroidcode.source.TheCatRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Kuan Wah Teo on 10/11/2020
 **/

@Module
class InteractorModule {
    @Provides
    @Singleton
    fun provideTheCatInteractor(theCatRepository: TheCatRepository): TheCatInteractor = DefaultTheCatInteractor(theCatRepository)

    @Provides
    @Singleton
    fun provideMyFavoriteInteractor(myFavoriteRepository: MyFavoriteRepository): MyFavoriteInteractor = DefaultMyFavoriteInteractor(myFavoriteRepository)
}
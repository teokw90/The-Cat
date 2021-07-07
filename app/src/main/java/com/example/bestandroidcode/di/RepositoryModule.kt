package com.example.bestandroidcode.di

import com.example.bestandroidcode.source.DefaultMyFavoriteRepository
import com.example.bestandroidcode.source.DefaultTheCatRepository
import com.example.bestandroidcode.source.MyFavoriteRepository
import com.example.bestandroidcode.source.TheCatRepository
import com.example.bestandroidcode.source.local.MyFavoriteLocalDataSource
import com.example.bestandroidcode.source.remote.TheCatRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Kuan Wah Teo on 07/11/2020
 **/

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideTheCatRepository(remoteDataSource: TheCatRemoteDataSource): TheCatRepository
        = DefaultTheCatRepository(remoteDataSource)

    @Provides
    @Singleton
    fun provideMyFavoriteRepository(localDataSource: MyFavoriteLocalDataSource): MyFavoriteRepository
        = DefaultMyFavoriteRepository(localDataSource)
}
package com.example.bestandroidcode.source

import com.example.bestandroidcode.utils.Result
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Kuan Wah Teo on 10/11/2020
 **/

@Singleton
class DefaultMyFavoriteRepository
@Inject constructor(private val localDataSource: MyFavoriteDataSource): MyFavoriteRepository {
    override suspend fun retrieveMyFavoriteList(): Result<List<String>> = localDataSource.retrieveMyFavoriteList()

    override suspend fun checkAndReturnFavoriteStatusFrom(catPhotoURL: String): Result<Boolean> = localDataSource.checkAndReturnFavoriteStatusFrom(catPhotoURL)

    override suspend fun addOrRemoveFromMyFavoriteListBy(catPhotoURL: String): Result<Boolean> = localDataSource.addOrRemoveFromMyFavoriteListBy(catPhotoURL)
}
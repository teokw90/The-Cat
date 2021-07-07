package com.example.bestandroidcode.source

import com.example.bestandroidcode.utils.Result

/**
 * Created by Kuan Wah Teo on 10/11/2020
 **/

interface MyFavoriteRepository {
    suspend fun retrieveMyFavoriteList(): Result<List<String>>

    suspend fun checkAndReturnFavoriteStatusFrom(catPhotoURL: String): Result<Boolean>

    suspend fun addOrRemoveFromMyFavoriteListBy(catPhotoURL: String): Result<Boolean>
}
package com.example.bestandroidcode.interactor

import com.example.bestandroidcode.utils.Result

/**
 * Created by Kuan Wah Teo on 10/11/2020
 **/

interface MyFavoriteInteractor {
    suspend fun retrieveMyFavoriteList(): Result<List<String>>

    suspend fun checkAndReturnFavoriteStatusFrom(catPhotoURL: String): Result<Boolean>

    suspend fun addOrRemoveFromMyFavoriteListBy(catPhotoURL: String): Result<Boolean>

    suspend fun removeAndReturnNewMyFavoriteList(catPhotoURL: String): Result<List<String>>
}
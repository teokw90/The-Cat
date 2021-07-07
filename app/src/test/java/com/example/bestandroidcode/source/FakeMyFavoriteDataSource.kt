package com.example.bestandroidcode.source

import com.example.bestandroidcode.utils.CustomResultErrorType
import com.example.bestandroidcode.utils.Result

/**
 * Created by Kuan Wah Teo on 10/11/2020
 **/

class FakeMyFavoriteDataSource(var myFavoriteList: MutableList<String>?): MyFavoriteDataSource {
    override suspend fun retrieveMyFavoriteList(): Result<List<String>> {
        myFavoriteList?.let {
            return Result.Success(data = it)
        }

        return Result.Error(errorCode = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.code,
            errorMessage = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.message)
    }

    override suspend fun checkAndReturnFavoriteStatusFrom(catPhotoURL: String): Result<Boolean> {
        var isFavoriteCatPhoto = false
        myFavoriteList?.let {
            isFavoriteCatPhoto = it.contains(catPhotoURL)
        }

        return Result.Success(data = isFavoriteCatPhoto)
    }

    override suspend fun addOrRemoveFromMyFavoriteListBy(catPhotoURL: String): Result<Boolean> {
        myFavoriteList?.let {
            if (it!!.contains(catPhotoURL)) {
                it!!.remove(catPhotoURL)
            } else {
                it!!.add(catPhotoURL)
            }

            return Result.Success(data = true)
        }

        return Result.Error(errorCode = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.code,
            errorMessage = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.message)
    }
}
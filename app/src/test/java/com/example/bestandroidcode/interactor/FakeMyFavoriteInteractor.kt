package com.example.bestandroidcode.interactor

import com.example.bestandroidcode.utils.CustomResultErrorType
import com.example.bestandroidcode.utils.Result

/**
 * Created by Kuan Wah Teo on 10/11/2020
 **/

class FakeMyFavoriteInteractor: MyFavoriteInteractor {
    private lateinit var myFavoriteList: List<String>

    override suspend fun retrieveMyFavoriteList(): Result<List<String>> {
        return if (::myFavoriteList.isInitialized) {
            Result.Success(data = myFavoriteList)
        } else {
            Result.Error(errorCode = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.code,
                errorMessage = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.message)
        }
    }

    override suspend fun checkAndReturnFavoriteStatusFrom(catPhotoURL: String): Result<Boolean> {
        return if (::myFavoriteList.isInitialized) {
            return Result.Success(data = myFavoriteList.contains(catPhotoURL))
        } else {
            Result.Error(errorCode = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.code,
                errorMessage = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.message)
        }
    }

    override suspend fun addOrRemoveFromMyFavoriteListBy(catPhotoURL: String): Result<Boolean> {
        return if (::myFavoriteList.isInitialized) {
            return Result.Success(data = true)
        } else {
            Result.Error(errorCode = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.code,
                errorMessage = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.message)
        }
    }

    override suspend fun removeAndReturnNewMyFavoriteList(catPhotoURL: String): Result<List<String>> {
        return if (::myFavoriteList.isInitialized) {
            return Result.Success(data = myFavoriteList)
        } else {
            Result.Error(errorCode = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.code,
                errorMessage = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.message)
        }
    }

    fun addMyFavoriteList(myFavoriteList: List<String>) {
        this.myFavoriteList = myFavoriteList
    }
}
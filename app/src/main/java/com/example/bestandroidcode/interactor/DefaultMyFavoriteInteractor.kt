package com.example.bestandroidcode.interactor

import com.example.bestandroidcode.source.MyFavoriteRepository
import com.example.bestandroidcode.utils.CustomResultErrorType
import com.example.bestandroidcode.utils.Result
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Kuan Wah Teo on 10/11/2020
 **/

@Singleton
class DefaultMyFavoriteInteractor
@Inject constructor(private val myFavoriteRepository: MyFavoriteRepository): MyFavoriteInteractor {
    override suspend fun retrieveMyFavoriteList(): Result<List<String>> = myFavoriteRepository.retrieveMyFavoriteList()

    override suspend fun checkAndReturnFavoriteStatusFrom(catPhotoURL: String): Result<Boolean> = myFavoriteRepository.checkAndReturnFavoriteStatusFrom(catPhotoURL)

    override suspend fun addOrRemoveFromMyFavoriteListBy(catPhotoURL: String): Result<Boolean>
        = myFavoriteRepository.addOrRemoveFromMyFavoriteListBy(catPhotoURL)

    override suspend fun removeAndReturnNewMyFavoriteList(catPhotoURL: String): Result<List<String>>
        = when(val result = myFavoriteRepository.addOrRemoveFromMyFavoriteListBy(catPhotoURL)) {
        is Result.Success -> {
            val isSuccessful = result.data
            if (isSuccessful) myFavoriteRepository.retrieveMyFavoriteList()
            else Result.Error(errorCode = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.code,
                errorMessage = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.message)
        }
        else -> Result.Error(errorCode = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.code,
            errorMessage = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.message)
    }


}
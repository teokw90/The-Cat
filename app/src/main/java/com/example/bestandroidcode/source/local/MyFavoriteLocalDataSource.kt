package com.example.bestandroidcode.source.local

import android.content.SharedPreferences
import com.example.bestandroidcode.BuildConfig
import com.example.bestandroidcode.source.MyFavoriteDataSource
import com.example.bestandroidcode.utils.CustomResultErrorType
import com.example.bestandroidcode.utils.Result
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Kuan Wah Teo on 10/11/2020
 **/

@Singleton
class MyFavoriteLocalDataSource
@Inject constructor(private val sharedPreference: SharedPreferences): MyFavoriteDataSource {
    override suspend fun retrieveMyFavoriteList(): Result<List<String>> {
        sharedPreference.getStringSet(BuildConfig.MyFavoriteCatList, HashSet())?.let {
            val myFavoriteList = it?.toTypedArray()?.toList()
            return Result.Success(data = myFavoriteList)
        }

        return Result.Error(errorCode = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.code,
            errorMessage = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.message)
    }

    override suspend fun checkAndReturnFavoriteStatusFrom(catPhotoURL: String): Result<Boolean> {
        var isFavoriteCatPhoto = false
        sharedPreference.getStringSet(BuildConfig.MyFavoriteCatList, HashSet())?.let {
            val myFavoriteList = it?.toTypedArray()?.toList()
            isFavoriteCatPhoto = myFavoriteList.contains(catPhotoURL)
        }

        return Result.Success(data = isFavoriteCatPhoto)
    }

    override suspend fun addOrRemoveFromMyFavoriteListBy(catPhotoURL: String): Result<Boolean> {
        sharedPreference.getStringSet(BuildConfig.MyFavoriteCatList, HashSet())?.let {
            if (it!!.contains(catPhotoURL)) {
                it!!.remove(catPhotoURL)
            } else {
                it!!.add(catPhotoURL)
            }

            val editor: SharedPreferences.Editor = sharedPreference.edit()
            editor.remove(BuildConfig.MyFavoriteCatList)
            editor.commit()

            editor.putStringSet(BuildConfig.MyFavoriteCatList, it)
            editor.commit()

            return Result.Success(data = true)
        }

        return Result.Error(errorCode = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.code,
            errorMessage = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.message)
    }
}
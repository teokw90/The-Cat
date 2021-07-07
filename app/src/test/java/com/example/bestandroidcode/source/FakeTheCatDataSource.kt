package com.example.bestandroidcode.source

import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.model.Category
import com.example.bestandroidcode.utils.CustomResultErrorType
import com.example.bestandroidcode.utils.Result

/**
 * Created by Kuan Wah Teo on 10/11/2020
 **/

class FakeTheCatDataSource(var categoryList: List<Category>?, var defaultCatPhotoURLList: List<Cat>?): TheCatDataSource {
    override suspend fun retrieveCategoryList(): Result<List<Category>> {
        categoryList?.let {
            return Result.Success(data = it)
        }

        return Result.Error(errorCode = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.code,
            errorMessage = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.message)
    }

    override suspend fun searchCatRandomly(): Result<List<Cat>> {
        defaultCatPhotoURLList?.let {
            return Result.Success(data = it)
        }

        return Result.Error(errorCode = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.code,
            errorMessage = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.message)
    }

    override suspend fun searchCatBasedOn(category: String): Result<List<Cat>> {
        defaultCatPhotoURLList?.let {
            return Result.Success(data = it)
        }

        return Result.Error(errorCode = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.code,
            errorMessage = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.message)
    }
}
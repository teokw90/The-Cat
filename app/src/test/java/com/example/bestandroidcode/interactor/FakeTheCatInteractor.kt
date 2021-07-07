package com.example.bestandroidcode.interactor

import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.model.Category
import com.example.bestandroidcode.utils.CustomResultErrorType
import com.example.bestandroidcode.utils.Result


/**
 * Created by Kuan Wah Teo on 10/11/2020
 **/

class FakeTheCatInteractor: TheCatInteractor {
    private lateinit var categoryList: List<Category>
    private lateinit var catList: List<Cat>

    override suspend fun retrieveCategoryList(): Result<List<Category>> {
        return if (::categoryList.isInitialized) {
            Result.Success(data = categoryList)
        } else {
            Result.Error(errorCode = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.code,
                errorMessage = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.message)
        }
    }

    override suspend fun searchCatRandomly(): Result<List<Cat>> {
        return if (::catList.isInitialized) {
            Result.Success(data = catList)
        } else {
            Result.Error(errorCode = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.code,
                errorMessage = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.message)
        }
    }

    override suspend fun searchCatBasedOn(category: String): Result<List<Cat>> {
        return if (::catList.isInitialized) {
            Result.Success(data = catList)
        } else {
            Result.Error(errorCode = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.code,
                errorMessage = CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.message)
        }
    }

    fun addCategoryList(categoryList: List<Category>) {
        this.categoryList = categoryList
    }

    fun addCatList(catList: List<Cat>) {
        this.catList = catList
    }
}
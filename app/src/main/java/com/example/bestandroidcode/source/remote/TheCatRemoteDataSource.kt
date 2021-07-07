package com.example.bestandroidcode.source.remote

import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.model.Category
import com.example.bestandroidcode.source.TheCatDataSource
import com.example.bestandroidcode.utils.Result
import com.example.bestandroidcode.utils.SafeApiExecutor
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Kuan Wah Teo on 07/11/2020
 **/

@Singleton
class TheCatRemoteDataSource
@Inject constructor(private val theCatWebService: TheCatWebService): TheCatDataSource {
    override suspend fun retrieveCategoryList(): Result<List<Category>> = SafeApiExecutor.execute {
        theCatWebService.retrieveCategoryList()
    }

    override suspend fun searchCatRandomly(): Result<List<Cat>> {
        val result = SafeApiExecutor.execute {
            theCatWebService.searchCatRandomly()
        }

        return composeAndReturn(result)
    }

    override suspend fun searchCatBasedOn(category: String): Result<List<Cat>> {
        val result = SafeApiExecutor.execute {
            theCatWebService.searchCatBasedOn(category)
        }

        return composeAndReturn(result)
    }

    private fun<T> composeAndReturn(result: Result<T>) = when(result) {
        is Result.Success -> Result.Success(data = result.data)
        else -> Result.Error((result as Result.Error).errorCode, result.errorMessage)
    }
}
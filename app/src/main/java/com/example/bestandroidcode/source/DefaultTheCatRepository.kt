package com.example.bestandroidcode.source

import com.example.bestandroidcode.model.Category
import com.example.bestandroidcode.utils.Result
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Kuan Wah Teo on 07/11/2020
 **/

@Singleton
class DefaultTheCatRepository
@Inject constructor(private val remoteDataSource: TheCatDataSource): TheCatRepository {
    override suspend fun retrieveCategoryList(): Result<List<Category>> = remoteDataSource.retrieveCategoryList()

    override suspend fun searchCatRandomly() = remoteDataSource.searchCatRandomly()

    override suspend fun searchCatBasedOn(category: String) = remoteDataSource.searchCatBasedOn(category)
}
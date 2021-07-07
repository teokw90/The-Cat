package com.example.bestandroidcode.interactor

import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.model.Category
import com.example.bestandroidcode.source.TheCatRepository
import com.example.bestandroidcode.utils.Result
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Kuan Wah Teo on 10/11/2020
 **/

@Singleton
class DefaultTheCatInteractor
@Inject constructor(private val theCatRepository: TheCatRepository): TheCatInteractor {
    override suspend fun retrieveCategoryList(): Result<List<Category>> = theCatRepository.retrieveCategoryList()

    override suspend fun searchCatRandomly(): Result<List<Cat>> = theCatRepository.searchCatRandomly()

    override suspend fun searchCatBasedOn(category: String): Result<List<Cat>> = theCatRepository.searchCatBasedOn(category)
}
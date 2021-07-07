package com.example.bestandroidcode.interactor

import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.model.Category
import com.example.bestandroidcode.utils.Result

/**
 * Created by Kuan Wah Teo on 10/11/2020
 **/
interface TheCatInteractor {
    suspend fun retrieveCategoryList(): Result<List<Category>>

    suspend fun searchCatRandomly(): Result<List<Cat>>

    suspend fun searchCatBasedOn(category: String): Result<List<Cat>>
}
package com.example.bestandroidcode.source.remote

import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.model.Category
import retrofit2.http.GET
import retrofit2.http.Query

interface TheCatWebService {
    @GET("/v1/categories")
    suspend fun retrieveCategoryList(): List<Category>

    @GET("/v1/images/search")
    suspend fun searchCatRandomly(): List<Cat>

    @GET("/v1/images/search")
    suspend fun searchCatBasedOn(@Query("category_ids") category_ids: String): List<Cat>
}
package com.example.bestandroidcode.interactor

import com.example.bestandroidcode.MainCoroutineRule
import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.model.Category
import com.example.bestandroidcode.source.FakeTheCatRepository
import com.example.bestandroidcode.source.TheCatRepository
import com.example.bestandroidcode.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Kuan Wah Teo on 10/11/2020
 **/
@ExperimentalCoroutinesApi
class DefaultTheCatInteractorTest {
    private val boxesCategory = Category(id = 5, name = "boxes")
    private val clothesCategory = Category(id = 15, name = "clothes")
    private val hatsCategory = Category(id = 1, name = "hats")
    private val sinksCategory = Category(id = 14, name = "sinks")
    private val spaceCategory = Category(id = 2, name = "space")
    private val sunglassesCategory = Category(id = 4, name = "sunglasses")
    private val tiesCategory = Category(id = 7, name = "ties")

    private val categoryList = listOf(boxesCategory, clothesCategory, hatsCategory, sinksCategory, spaceCategory, sunglassesCategory, tiesCategory)

    private val cat = Cat(height = 768, id = "360", url = "https://cdn2.thecatapi.com/images/360.jpg", width = 1024)

    private val catList = listOf(cat)

    private lateinit var theCatRepository: TheCatRepository

    // Class under test
    private lateinit var searchInteractor: DefaultTheCatInteractor

    // Set the main coroutines dispatcher for unit testing
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun createInteractor() {
        theCatRepository = FakeTheCatRepository(categoryList, catList)

        // Get a reference to class under test
        searchInteractor = DefaultTheCatInteractor(theCatRepository)
    }

    @Test
    fun retrieveCategoryList_ReturnCategoryListFromDataSource() = mainCoroutineRule.runBlockingTest {
        val result = searchInteractor.retrieveCategoryList() as Result.Success
        assertThat(result.data, equalTo(categoryList))
    }

    @Test
    fun searchCatRandomly_ReturnCatListFromDataSource() = mainCoroutineRule.runBlockingTest {
        val result = searchInteractor.searchCatRandomly() as Result.Success
        assertThat(result.data, equalTo(catList))
    }

    @Test
    fun searchCatBasedOnCategory_ReturnCatListFromDataSource() = mainCoroutineRule.runBlockingTest {
        val result = searchInteractor.searchCatBasedOn(boxesCategory.name) as Result.Success
        assertThat(result.data, equalTo(catList))
    }
}
package com.example.bestandroidcode.source

import com.example.bestandroidcode.MainCoroutineRule
import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.model.Category
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
class DefaultTheCatRepositoryTest {
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

    private lateinit var theCatDataSource: TheCatDataSource

    // Class under test
    private lateinit var theCatRepository: DefaultTheCatRepository

    // Set the main coroutines dispatcher for unit testing
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun createRepository() {
        theCatDataSource = FakeTheCatDataSource(categoryList, catList)

        // Get a reference to class under test
        theCatRepository = DefaultTheCatRepository(theCatDataSource)
    }

    @Test
    fun retrieveCategoryList_ReturnCategoryListFromDataSource() = mainCoroutineRule.runBlockingTest {
        val result = theCatRepository.retrieveCategoryList() as Result.Success
        assertThat(result.data, equalTo(categoryList))
    }

    @Test
    fun searchCatRandomly_ReturnCatListFromDataSource() = mainCoroutineRule.runBlockingTest {
        val result = theCatRepository.searchCatRandomly() as Result.Success
        assertThat(result.data, equalTo(catList))
    }

    @Test
    fun searchCatBasedOnCategory_ReturnCatListFromDataSource() = mainCoroutineRule.runBlockingTest {
        val result = theCatRepository.searchCatBasedOn(boxesCategory.name) as Result.Success
        assertThat(result.data, equalTo(catList))
    }
}
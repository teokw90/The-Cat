package com.example.bestandroidcode.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.bestandroidcode.MainCoroutineRule
import com.example.bestandroidcode.getOrAwaitValue
import com.example.bestandroidcode.interactor.FakeMyFavoriteInteractor
import com.example.bestandroidcode.interactor.FakeTheCatInteractor
import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.model.Category
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Kuan Wah Teo on 10/11/2020
 **/
@ExperimentalCoroutinesApi
class TheCatViewModelTest {
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

    private val myFavoriteList = listOf("https://cdn2.thecatapi.com/images/360.jpg", "https://cdn2.thecatapi.com/images/nIoQyeLs8.png", "https://cdn2.thecatapi.com/images/91i.jpg", "https://cdn2.thecatapi.com/images/32l.jpg", "https://cdn2.thecatapi.com/images/cge.jpg")

    // Use fake interactor to be injected into viewModel
    private lateinit var myFavoriteInteractor: FakeMyFavoriteInteractor
    private lateinit var theCatInteractor: FakeTheCatInteractor

    // Subject under test
    private lateinit var theCatViewModel: TheCatViewModel

    // Executes each task synchronously using Architecture Components
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupViewModel() {
        myFavoriteInteractor = FakeMyFavoriteInteractor()
        myFavoriteInteractor.addMyFavoriteList(myFavoriteList)

        theCatInteractor = FakeTheCatInteractor()
        theCatInteractor.addCategoryList(categoryList)
        theCatInteractor.addCatList(catList)

        // Given a fresh ViewModel
        theCatViewModel = TheCatViewModel(theCatInteractor, myFavoriteInteractor)
    }

    @Test
    fun retrieveCategoryList_returnCategoryList() {
        theCatViewModel.retrieveCategoryList()

        assertThat(theCatViewModel.categoryList.getOrAwaitValue(), equalTo(categoryList))
    }

    @Test
    fun searchCatRandomly_returnCatListAndIsFavoriteCatPhoto() {
        theCatViewModel.searchCatRandomly()

        assertThat(theCatViewModel.catPhotoURL.getOrAwaitValue(), equalTo("https://cdn2.thecatapi.com/images/360.jpg"))
        assertThat(theCatViewModel.isFavoriteCatPhoto.getOrAwaitValue(), equalTo(true))
    }

    @Test
    fun searchCatBasedOnCategory_returnCatListAndIsFavoriteCatPhoto() {
        theCatViewModel.retrieveCategoryList()
        assertThat(theCatViewModel.categoryList.getOrAwaitValue(), equalTo(categoryList))

        theCatViewModel.searchCatBasedOn(boxesCategory.name)

        assertThat(theCatViewModel.catPhotoURL.getOrAwaitValue(), equalTo("https://cdn2.thecatapi.com/images/360.jpg"))
        assertThat(theCatViewModel.isFavoriteCatPhoto.getOrAwaitValue(), equalTo(true))
    }

    @Test
    fun saveOrRemoveFromMyFavorite_returnIsFavoriteCatPhoto() {
        theCatViewModel.searchCatRandomly()
        assertThat(theCatViewModel.catPhotoURL.getOrAwaitValue(), equalTo("https://cdn2.thecatapi.com/images/360.jpg"))

        theCatViewModel.saveOrRemoveFromMyFavorite()
        assertThat(theCatViewModel.isFavoriteCatPhoto.getOrAwaitValue(), equalTo(true))
    }
}
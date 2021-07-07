package com.example.bestandroidcode.interactor

import com.example.bestandroidcode.MainCoroutineRule
import com.example.bestandroidcode.source.FakeMyFavoriteRepository
import com.example.bestandroidcode.source.MyFavoriteRepository
import com.example.bestandroidcode.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Kuan Wah Teo on 10/11/2020
 **/
@ExperimentalCoroutinesApi
class DefaultMyFavoriteInteractorTest {
    private val myFavoriteList = listOf("https://cdn2.thecatapi.com/images/nIoQyeLs8.png", "https://cdn2.thecatapi.com/images/91i.jpg", "https://cdn2.thecatapi.com/images/32l.jpg", "https://cdn2.thecatapi.com/images/cge.jpg")

    private lateinit var myFavoriteRepository: MyFavoriteRepository

    // Class under test
    private lateinit var myFavoriteInteractor: DefaultMyFavoriteInteractor

    // Set the main coroutines dispatcher for unit testing
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun createInteractor() {
        myFavoriteRepository = FakeMyFavoriteRepository(myFavoriteList.toMutableList())

        // Get a reference to class under test
        myFavoriteInteractor = DefaultMyFavoriteInteractor(myFavoriteRepository)
    }

    @Test
    fun retrieveMyFavoriteList_requestMyFavoriteListFromRepository() = mainCoroutineRule.runBlockingTest {
        val result = myFavoriteRepository.retrieveMyFavoriteList() as Result.Success
        assertThat(result.data, Matchers.equalTo(myFavoriteList))
    }

    @Test
    fun checkCatPhotoURL_ReturnIsFavoriteFromRepository() = mainCoroutineRule.runBlockingTest {
        val result = myFavoriteRepository.checkAndReturnFavoriteStatusFrom("https://cdn2.thecatapi.com/images/nIoQyeLs8.png") as Result.Success
        assertThat(result.data, Matchers.equalTo(true))
    }

    @Test
    fun checkCatPhotoURL_ReturnNotFavoriteFromRepository() = mainCoroutineRule.runBlockingTest {
        val result = myFavoriteRepository.checkAndReturnFavoriteStatusFrom("https://cdn2.thecatapi.com/images/nIoQyeLs9.png") as Result.Success
        assertThat(result.data, Matchers.equalTo(false))
    }

    @Test
    fun addOrRemoveCatPhoto_ReturnTrueFromRepository() = mainCoroutineRule.runBlockingTest {
        val result = myFavoriteRepository.addOrRemoveFromMyFavoriteListBy("https://cdn2.thecatapi.com/images/nIoQyeLs9.png") as Result.Success
        assertThat(result.data, Matchers.equalTo(true))
    }
}
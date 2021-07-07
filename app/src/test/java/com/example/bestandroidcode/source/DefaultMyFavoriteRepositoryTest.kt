package com.example.bestandroidcode.source

import com.example.bestandroidcode.MainCoroutineRule
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
class DefaultMyFavoriteRepositoryTest {
    private val myFavoriteList = listOf("https://cdn2.thecatapi.com/images/nIoQyeLs8.png", "https://cdn2.thecatapi.com/images/91i.jpg", "https://cdn2.thecatapi.com/images/32l.jpg", "https://cdn2.thecatapi.com/images/cge.jpg")

    private lateinit var myFavoriteDataSource: MyFavoriteDataSource

    // Class under test
    private lateinit var myFavoriteRepository: DefaultMyFavoriteRepository

    // Set the main coroutines dispatcher for unit testing
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun createRepository() {
        myFavoriteDataSource = FakeMyFavoriteDataSource(myFavoriteList.toMutableList())

        // Get a reference to class under test
        myFavoriteRepository = DefaultMyFavoriteRepository(myFavoriteDataSource)
    }

    @Test
    fun retrieveMyFavoriteList_requestMyFavoriteListFromDataSource() = mainCoroutineRule.runBlockingTest {
        val result = myFavoriteRepository.retrieveMyFavoriteList() as Result.Success
        assertThat(result.data, equalTo(myFavoriteList))
    }

    @Test
    fun checkCatPhotoURL_ReturnIsFavoriteFromDataSource() = mainCoroutineRule.runBlockingTest {
        val result = myFavoriteRepository.checkAndReturnFavoriteStatusFrom("https://cdn2.thecatapi.com/images/nIoQyeLs8.png") as Result.Success
        assertThat(result.data, equalTo(true))
    }

    @Test
    fun checkCatPhotoURL_ReturnNotFavoriteFromDataSource() = mainCoroutineRule.runBlockingTest {
        val result = myFavoriteRepository.checkAndReturnFavoriteStatusFrom("https://cdn2.thecatapi.com/images/nIoQyeLs9.png") as Result.Success
        assertThat(result.data, equalTo(false))
    }

    @Test
    fun addOrRemoveCatPhoto_ReturnTrueFromDataSource() = mainCoroutineRule.runBlockingTest {
        val result = myFavoriteRepository.addOrRemoveFromMyFavoriteListBy("https://cdn2.thecatapi.com/images/nIoQyeLs9.png") as Result.Success
        assertThat(result.data, equalTo(true))
    }
}
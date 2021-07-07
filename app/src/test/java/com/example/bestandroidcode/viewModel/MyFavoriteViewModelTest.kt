package com.example.bestandroidcode.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.bestandroidcode.MainCoroutineRule
import com.example.bestandroidcode.getOrAwaitValue
import com.example.bestandroidcode.interactor.FakeMyFavoriteInteractor
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
class MyFavoriteViewModelTest {
    private val myFavoriteList = listOf("https://cdn2.thecatapi.com/images/nIoQyeLs8.png", "https://cdn2.thecatapi.com/images/91i.jpg", "https://cdn2.thecatapi.com/images/32l.jpg", "https://cdn2.thecatapi.com/images/cge.jpg")

    // Use a fake interactor to be injected into viewModel
    private lateinit var myFavoriteInteractor: FakeMyFavoriteInteractor

    // Subject under test
    private lateinit var myFavoriteViewModel: MyFavoriteViewModel

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

        // Given a fresh ViewModel
        myFavoriteViewModel = MyFavoriteViewModel(myFavoriteInteractor)
    }

    @Test
    fun retrieveMyFavoriteList_returnMyFavoriteList() {
        myFavoriteViewModel.retrieveMyFavoriteList()

        assertThat(myFavoriteViewModel.myFavoriteList.getOrAwaitValue(), equalTo(myFavoriteList))
    }
}
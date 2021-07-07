package com.example.bestandroidcode.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bestandroidcode.interactor.MyFavoriteInteractor
import com.example.bestandroidcode.utils.CustomResultErrorType
import com.example.bestandroidcode.utils.Result
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * Created by Kuan Wah Teo on 10/11/2020
 **/

class MyFavoriteViewModel
@Inject constructor(private val myFavoriteInteractor: MyFavoriteInteractor): ViewModel() {
    private var viewModelJob = Job()
    private var coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val _myFavoriteList = MutableLiveData<List<String>>()
    val myFavoriteList: LiveData<List<String>>
        get() = _myFavoriteList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun retrieveMyFavoriteList() {
        coroutineScope.launch {
            var result: Result<List<String>> = myFavoriteInteractor.retrieveMyFavoriteList()

            checkAndReturnMyFavoriteListFrom(result)
        }
    }

    private suspend fun checkAndReturnMyFavoriteListFrom(result: Result<List<String>>) {
        when(result) {
            is Result.Success -> {
                val data = result.data
                if (data.isNotEmpty()) {
                    withContext(Dispatchers.Main) {
                        _myFavoriteList.value = data
                    }
                } else {
                    returnErrorMessage(CustomResultErrorType.SOMETHING_WENT_WRONG_ERROR.message)
                }
            }
            else -> {
                val errorResult = (result as Result.Error)
                errorResult.errorMessage?.let { returnErrorMessage(it) }
            }
        }
    }

    private suspend fun returnErrorMessage(errorMessage: String) {
        withContext(Dispatchers.Main) {
            _errorMessage.value = errorMessage
        }
    }
}
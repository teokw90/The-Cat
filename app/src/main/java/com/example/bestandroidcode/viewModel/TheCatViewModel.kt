package com.example.bestandroidcode.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bestandroidcode.interactor.MyFavoriteInteractor
import com.example.bestandroidcode.interactor.TheCatInteractor
import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.model.Category
import com.example.bestandroidcode.utils.CustomResultErrorType
import com.example.bestandroidcode.utils.Result
import kotlinx.coroutines.*
import javax.inject.Inject

class TheCatViewModel
@Inject constructor(private val theCatInteractor: TheCatInteractor, private val myFavoriteInteractor: MyFavoriteInteractor): ViewModel() {
    private var viewModelJob = Job()
    private var coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val _categoryList = MutableLiveData<List<Category>>()
    val categoryList: LiveData<List<Category>>
        get() = _categoryList

    private val _catPhotoURL = MutableLiveData<String>()
    val catPhotoURL: LiveData<String>
        get() = _catPhotoURL

    private val _isFavoriteCatPhoto = MutableLiveData<Boolean>()
    val isFavoriteCatPhoto: LiveData<Boolean>
        get() = _isFavoriteCatPhoto

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun retrieveCategoryList() {
        coroutineScope.launch {
            var result: Result<List<Category>> = theCatInteractor.retrieveCategoryList()

            checkAndReturnCategoryListFrom(result)
        }
    }

    private suspend fun checkAndReturnCategoryListFrom(result: Result<List<Category>>) {
        when(result) {
            is Result.Success -> {
                val data = result.data
                if (data.isNotEmpty()) {
                    withContext(Dispatchers.Main) {
                        _categoryList.value = data
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

    fun searchCatRandomly() {
        coroutineScope.launch {
            var result: Result<List<Cat>> = theCatInteractor.searchCatRandomly()

            checkAndReturnSearchResultFrom(result)
        }
    }

    fun searchCatBasedOn(selectedCategory: String) {
        val category = categoryList.value?.find { category -> category.name == selectedCategory }

        coroutineScope.launch {
            category?.let {
                var result: Result<List<Cat>> = theCatInteractor.searchCatBasedOn(it?.id.toString())
                checkAndReturnSearchResultFrom(result)
            }
        }
    }

    private suspend fun checkAndReturnSearchResultFrom(result: Result<List<Cat>>) {
        when(result) {
            is Result.Success -> {
                val data = result.data
                if (data.isNotEmpty()) {
                    data[0].url?.let {
                        returnCatImageURL(it)
                        checkAndReturnFavoriteStatusFrom(it)
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

    private suspend fun returnCatImageURL(catImageURL: String) {
        withContext(Dispatchers.Main) {
            _catPhotoURL.value = catImageURL
        }
    }

    private suspend fun returnErrorMessage(errorMessage: String) {
        withContext(Dispatchers.Main) {
            _errorMessage.value = errorMessage
        }
    }

   private suspend fun checkAndReturnFavoriteStatusFrom(catPhotoURL: String) {
        val result = myFavoriteInteractor.checkAndReturnFavoriteStatusFrom(catPhotoURL)

        withContext(Dispatchers.Main) {
            when(result) {
                is Result.Success -> _isFavoriteCatPhoto.value = result.data
                else -> _isFavoriteCatPhoto.value = false
            }
        }
    }

    fun saveOrRemoveFromMyFavorite() {
        coroutineScope.launch {
            catPhotoURL.value?.let {
                val result = myFavoriteInteractor.addOrRemoveFromMyFavoriteListBy(it)
                checkAndGetFavoriteStatusFrom(result, it)
            }
        }
    }

    private suspend fun checkAndGetFavoriteStatusFrom(result: Result<Boolean>, catPhotoURL: String) {
        when(result) {
            is Result.Success -> {
                val isAddOrRemoveSuccessful = result.data
                if (isAddOrRemoveSuccessful) {
                    checkAndReturnFavoriteStatusFrom(catPhotoURL)
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
    
    fun reset() {
        _catPhotoURL.postValue(null)
        _errorMessage.postValue(null)
        _isFavoriteCatPhoto.value = false
    }
}

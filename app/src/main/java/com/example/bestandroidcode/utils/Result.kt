package com.example.bestandroidcode.utils

/**
 * Created by Kuan Wah Teo on 07/11/2020
 **/

sealed class Result<out R> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val errorCode: Int, val errorMessage: String?): Result<Nothing>()
    object Loading: Result<Nothing>()

    override fun toString(): String {
        return when(this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[errorCode=$errorCode, errorMessage=$errorMessage]"
            Loading -> "Loading"
        }
    }
}

enum class CustomResultErrorType(val code: Int, val message: String) {
    NETWORK_ERROR(400, "No network connection"),
    SOMETHING_WENT_WRONG_ERROR(999, "Something went wrong.")
}

/**
 * 'true' if [Result] is of type [Success] & holds non-null [Success.data].
 */

val Result<*>.succeeded
    get() = this is Result.Success && data != null
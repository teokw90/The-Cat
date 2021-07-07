package com.example.bestandroidcode.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Kuan Wah Teo on 07/11/2020
 **/
class SafeApiExecutor {
    companion object {
        suspend fun<T> execute(apiCall: suspend() -> T): Result<T> {
            return withContext(Dispatchers.IO) {
                try {
                    Result.Success(apiCall.invoke())
                } catch (throwable: Throwable) {
                    when (throwable) {
                        is IOException -> Result.Error(errorCode = 9999, errorMessage = throwable.message)
                        is HttpException -> Result.Error(errorCode = throwable.code(), errorMessage = throwable.message())
                        else -> Result.Error(errorCode = -1, errorMessage = "Unknown")
                    }
                }
            }
        }
    }
}
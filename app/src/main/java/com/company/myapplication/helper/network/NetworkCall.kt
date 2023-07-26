package com.company.myapplication.helper.network

import com.company.myapplication.helper.model.GenericErrorResponse
import com.company.myapplication.helper.model.GenericResponse
import com.company.myapplication.helper.utils.console
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException

object NetworkCall {
    @Suppress("UNCHECKED_CAST")
    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO, apiCall: suspend () -> T
    ): ApiResult<T> {
        return withContext(dispatcher) {
            try {
                val result: T = apiCall.invoke()
                ApiResult.Success(result)
            } catch (throwable: Exception) {
                when (throwable) {
                    is HttpException -> {
                        val jObjError = JSONObject(
                            throwable.response()?.errorBody()?.string()
                                ?: "Couldn't reach the server. Please check your internet connection"
                        )
                        val errorResponse = convertErrorBody(jObjError)

                        ApiResult.Error(errorResponse)

                    }

                    else -> {
                        console("safeApiCall: Error => ${throwable.localizedMessage}")
                        if (throwable.localizedMessage?.contains("End of input at line 1 column 1 path") == true) {
                            val genericResponse = GenericResponse("")
                            ApiResult.Success(genericResponse as T)
                        } else {
                            val errorJSONObject = JSONObject()
                            errorJSONObject.put(
                                "errorMessage", "Couldn't reach the server. Please try again later!"
                            )
                            val errorResponse = convertErrorBody(errorJSONObject)
                            ApiResult.Error(errorResponse)
                        }
                    }
                }
            }
        }
    }

    private fun convertErrorBody(jObjError: JSONObject): GenericErrorResponse? {
        val gson = Gson()
        return try {
            gson.fromJson(
                jObjError.toString(), GenericErrorResponse::class.java
            )
        } catch (exception: Exception) {
            null
        }
    }
}
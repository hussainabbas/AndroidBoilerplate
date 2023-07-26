package com.company.myapplication.helper.network

import com.company.myapplication.helper.model.GenericErrorResponse


sealed class ApiResult<T>(val data: T? = null, val error: GenericErrorResponse? = null) {
    class Success<T>(data: T) : ApiResult<T>(data)
    class Error<T>(error: GenericErrorResponse?, data: T? = null) : ApiResult<T>(data, error)
    //class Loading<T>(data: T? = null) : ApiResult<T>(data)
}


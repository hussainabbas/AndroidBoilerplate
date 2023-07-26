package com.company.myapplication.data.remote

import com.company.myapplication.helper.model.GenericResponse
import javax.inject.Inject

class ApiResponseImpl @Inject constructor(
    private val api: ApiResponse
) : ApiResponse {
    override suspend fun test(): GenericResponse {
        return api.test()
    }
}


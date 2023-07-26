package com.company.myapplication.data.remote

import com.company.myapplication.helper.model.GenericResponse
import com.company.myapplication.helper.network.ApiEndPoints
import retrofit2.http.GET

interface ApiResponse {
    @GET(ApiEndPoints.TEST)
    suspend fun test(): GenericResponse
}

@Target(AnnotationTarget.FUNCTION)
annotation class Authenticated
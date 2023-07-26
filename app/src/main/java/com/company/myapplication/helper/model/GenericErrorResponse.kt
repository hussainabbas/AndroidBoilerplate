package com.company.myapplication.helper.model

data class GenericErrorResponse(
    val errors: Errors,
    val errorMessage: String,
    val errorType: String,
    val path: String,
    val statusCode: Int,
)

data class Errors(
    val errorMessage: String,
)

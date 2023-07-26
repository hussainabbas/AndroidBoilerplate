package com.company.myapplication.domain.repository

import com.company.myapplication.helper.model.GenericResponse


interface Repository {
    suspend fun test(): GenericResponse
}
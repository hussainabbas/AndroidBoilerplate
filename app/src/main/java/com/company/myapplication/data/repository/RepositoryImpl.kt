package com.company.myapplication.data.repository

import com.company.myapplication.data.remote.ApiResponse
import com.company.myapplication.domain.repository.Repository
import com.company.myapplication.helper.model.GenericResponse
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val api: ApiResponse) : Repository {
    override suspend fun test(): GenericResponse {
        return api.test()
    }
}
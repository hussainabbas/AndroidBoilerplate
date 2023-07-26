package com.company.myapplication.di

import com.company.myapplication.data.repository.RepositoryImpl
import com.company.myapplication.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiModuleImpl {
    @Binds
    abstract fun provideRepository(
        repositoryImpl: RepositoryImpl
    ): Repository

}



package com.personal.tmdb.home.di

import com.personal.tmdb.home.data.repository.HomeRepositoryImpl
import com.personal.tmdb.home.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHomeRepository(
        homeRepository: HomeRepositoryImpl
    ): HomeRepository
}
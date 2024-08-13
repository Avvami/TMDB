package com.personal.tmdb.core.di

import com.personal.tmdb.core.data.repository.LocalCacheImpl
import com.personal.tmdb.core.data.repository.LocalRepositoryImpl
import com.personal.tmdb.core.domain.repository.LocalCache
import com.personal.tmdb.core.domain.repository.LocalRepository
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
    abstract fun bindLocalRepository(
        localRepositoryImpl: LocalRepositoryImpl
    ): LocalRepository

    @Binds
    @Singleton
    abstract fun bindLocalCache(
        localCacheImpl: LocalCacheImpl
    ): LocalCache
}
package com.personal.tmdb.core.data.repository

import com.personal.tmdb.core.data.local.PreferencesDao
import com.personal.tmdb.core.data.local.PreferencesEntity
import com.personal.tmdb.core.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val preferencesDao: PreferencesDao
): LocalRepository {
    override fun getPreferences(): Flow<PreferencesEntity> = preferencesDao.getPreferences()

    override suspend fun setTheme(darkTheme: Boolean?) = preferencesDao.setTheme(darkTheme)

    override suspend fun setSessionId(sessionId: String) = preferencesDao.setSessionId(sessionId)

    override suspend fun setLanguage(language: String) = preferencesDao.setLanguage(language)

    override suspend fun setCorners(corners: Int) = preferencesDao.setCorners(corners)

    override suspend fun setUseCards(useCards: Boolean) = preferencesDao.setUseCards(useCards)

    override suspend fun setShowTitle(showTitle: Boolean) = preferencesDao.setShowTitle(showTitle)

    override suspend fun setShowVoteAverage(showVoteAverage: Boolean) = preferencesDao.setShowVoteAverage(showVoteAverage)
}
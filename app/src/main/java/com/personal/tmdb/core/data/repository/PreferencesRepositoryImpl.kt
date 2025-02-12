package com.personal.tmdb.core.data.repository

import com.personal.tmdb.core.data.local.PreferencesDao
import com.personal.tmdb.core.data.mappers.toPreferences
import com.personal.tmdb.core.domain.models.Preferences
import com.personal.tmdb.core.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val preferencesDao: PreferencesDao
): PreferencesRepository {
    override fun getPreferences(): Flow<Preferences> = preferencesDao.getPreferencesFlow().map { it.toPreferences() }

    override suspend fun getLanguage(): String = preferencesDao.getLanguage()

    override suspend fun setTheme(darkTheme: Boolean?) = preferencesDao.setTheme(darkTheme)

    override suspend fun setLanguage(language: String) = preferencesDao.setLanguage(language)

    override suspend fun setShowTitle(showTitle: Boolean) = preferencesDao.setShowTitle(showTitle)

    override suspend fun setShowVoteAverage(showVoteAverage: Boolean) = preferencesDao.setShowVoteAverage(showVoteAverage)

    override suspend fun setAdditionalNavigationItem(navigationItem: String) = preferencesDao.setAdditionNavigationItem(navigationItem)
}
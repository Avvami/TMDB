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

    override suspend fun setDarkMode(isDark: Boolean) = preferencesDao.setDarkMode(isDark)

    override suspend fun setSessionId(sessionId: String) = preferencesDao.setSessionId(sessionId)
}
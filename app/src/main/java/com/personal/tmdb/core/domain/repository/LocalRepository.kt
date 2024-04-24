package com.personal.tmdb.core.domain.repository

import com.personal.tmdb.core.data.local.PreferencesEntity
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    fun getPreferences(): Flow<PreferencesEntity>

    suspend fun setDarkMode(isDark: Boolean)

    suspend fun setSessionId(sessionId: String)
}
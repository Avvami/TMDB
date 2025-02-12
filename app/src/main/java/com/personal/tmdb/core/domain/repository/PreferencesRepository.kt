package com.personal.tmdb.core.domain.repository

import com.personal.tmdb.core.domain.models.Preferences
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    fun getPreferences(): Flow<Preferences>

    suspend fun getLanguage(): String

    suspend fun setTheme(darkTheme: Boolean?)

    suspend fun setLanguage(language: String)

    suspend fun setShowTitle(showTitle: Boolean)

    suspend fun setShowVoteAverage(showVoteAverage: Boolean)

    suspend fun setAdditionalNavigationItem(navigationItem: String)
}
package com.personal.tmdb.core.domain.repository

import com.personal.tmdb.core.data.local.PreferencesEntity
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    fun getPreferences(): Flow<PreferencesEntity>

    suspend fun setTheme(darkTheme: Boolean?)

    suspend fun setAccessInfo(accessToken: String, sessionId: String, accountId: String)

    suspend fun setLanguage(language: String)

    suspend fun setCorners(corners: Int)

    suspend fun setUseCards(useCards: Boolean)

    suspend fun setShowTitle(showTitle: Boolean)

    suspend fun setShowVoteAverage(showVoteAverage: Boolean)
}
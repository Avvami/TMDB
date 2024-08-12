package com.personal.tmdb.core.data.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PreferencesDao {
    @Query("SELECT * FROM preferencesentity")
    fun getPreferences(): Flow<PreferencesEntity>

    @Query("UPDATE preferencesentity SET darkTheme = :darkTheme")
    suspend fun setTheme(darkTheme: Boolean?)

    @Query("UPDATE preferencesentity SET sessionId = :sessionId")
    suspend fun setSessionId(sessionId: String)

    @Query("UPDATE preferencesentity SET language = :language")
    suspend fun setLanguage(language: String)

    @Query("UPDATE preferencesentity SET corners = :corners")
    suspend fun setCorners(corners: Int)

    @Query("UPDATE preferencesentity SET useCards = :useCards")
    suspend fun setUseCards(useCards: Boolean)

    @Query("UPDATE preferencesentity SET showTitle = :showTitle")
    suspend fun setShowTitle(showTitle: Boolean)

    @Query("UPDATE preferencesentity SET showVoteAverage = :showVoteAverage")
    suspend fun setShowVoteAverage(showVoteAverage: Boolean)
}
package com.personal.tmdb.core.data.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PreferencesDao {
    @Query("SELECT * FROM preferencesentity")
    fun getPreferencesFlow(): Flow<PreferencesEntity>

    @Query("SELECT language FROM preferencesentity")
    suspend fun getLanguage(): String

    @Query("UPDATE preferencesentity SET darkTheme = :darkTheme")
    suspend fun setTheme(darkTheme: Boolean?)

    @Query("UPDATE preferencesentity SET language = :language")
    suspend fun setLanguage(language: String)

    @Query("UPDATE preferencesentity SET showTitle = :showTitle")
    suspend fun setShowTitle(showTitle: Boolean)

    @Query("UPDATE preferencesentity SET showVoteAverage = :showVoteAverage")
    suspend fun setShowVoteAverage(showVoteAverage: Boolean)

    @Query("UPDATE preferencesentity SET additionalNavigationItem = :navigationItem")
    suspend fun setAdditionNavigationItem(navigationItem: String)
}
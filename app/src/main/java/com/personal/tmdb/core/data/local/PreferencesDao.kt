package com.personal.tmdb.core.data.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PreferencesDao {
    @Query("SELECT * FROM preferencesentity")
    fun getPreferences(): Flow<PreferencesEntity>

    @Query("UPDATE preferencesentity SET isDark = :isDark")
    suspend fun setDarkMode(isDark: Boolean)

    @Query("UPDATE preferencesentity SET sessionId = :sessionId")
    suspend fun setSessionId(sessionId: String)
}
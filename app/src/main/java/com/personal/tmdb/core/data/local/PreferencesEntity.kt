package com.personal.tmdb.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PreferencesEntity(
    @PrimaryKey val id: Int = 0,
    val isDark: Boolean,
    val sessionId: String
)

package com.personal.tmdb.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PreferencesEntity(
    @PrimaryKey val id: Int = 0,
    val darkTheme: Boolean? = null,
    val accessToken: String,
    val accountId: String,
    val sessionId: String,
    val language: String,
    val corners: Int,
    val useCards: Boolean,
    val showTitle: Boolean,
    val showVoteAverage: Boolean
)

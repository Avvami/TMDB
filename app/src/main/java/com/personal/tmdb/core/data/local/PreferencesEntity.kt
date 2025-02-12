package com.personal.tmdb.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PreferencesEntity(
    @PrimaryKey val id: Int = 0,
    val darkTheme: Boolean? = null,
    val language: String,
    val showTitle: Boolean,
    val showVoteAverage: Boolean,
    val additionalNavigationItem: String
)

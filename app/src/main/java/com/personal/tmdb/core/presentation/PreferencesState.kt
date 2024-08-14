package com.personal.tmdb.core.presentation

data class PreferencesState(
    val darkTheme: Boolean? = null,
    val language: String = "",
    val corners: Int = 0,
    val useCards: Boolean = false,
    val showTitle: Boolean = false,
    val showVoteAverage: Boolean = false
)

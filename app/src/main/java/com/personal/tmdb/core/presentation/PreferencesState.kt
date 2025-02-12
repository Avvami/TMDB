package com.personal.tmdb.core.presentation

import com.personal.tmdb.core.domain.util.AdditionalNavigationItem

data class PreferencesState(
    val darkTheme: Boolean? = null,
    val language: String = "",
    val showTitle: Boolean = false,
    val showVoteAverage: Boolean = false,
    val additionalNavigationItem: AdditionalNavigationItem = AdditionalNavigationItem.WATCHLIST
)

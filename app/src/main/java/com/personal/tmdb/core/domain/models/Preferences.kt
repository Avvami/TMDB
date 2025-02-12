package com.personal.tmdb.core.domain.models

import com.personal.tmdb.core.domain.util.AdditionalNavigationItem

data class Preferences(
    val darkTheme: Boolean? = null,
    val language: String,
    val showTitle: Boolean,
    val showVoteAverage: Boolean,
    val additionalNavigationItem: AdditionalNavigationItem
)

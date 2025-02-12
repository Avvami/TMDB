package com.personal.tmdb.core.data.mappers

import com.personal.tmdb.core.data.local.PreferencesEntity
import com.personal.tmdb.core.domain.models.Preferences
import com.personal.tmdb.core.domain.util.AdditionalNavigationItem

fun PreferencesEntity.toPreferences(): Preferences {
    return Preferences(
        darkTheme = darkTheme,
        language = language,
        showTitle = showTitle,
        showVoteAverage = showVoteAverage,
        additionalNavigationItem = AdditionalNavigationItem.from(additionalNavigationItem)
    )
}
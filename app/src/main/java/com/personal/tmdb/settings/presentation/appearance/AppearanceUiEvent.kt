package com.personal.tmdb.settings.presentation.appearance

import com.personal.tmdb.core.domain.util.AdditionalNavigationItem

sealed interface AppearanceUiEvent {
    data object OnNavigateBack: AppearanceUiEvent
    data class SetShowTitle(val showTitle: Boolean): AppearanceUiEvent
    data class SetShowVoteAverage(val showVoteAverage: Boolean): AppearanceUiEvent
    data class SetAdditionalNavItem(val item: AdditionalNavigationItem): AppearanceUiEvent
    data class SetTheme(val darkTheme: Boolean?): AppearanceUiEvent
}
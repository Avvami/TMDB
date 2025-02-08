package com.personal.tmdb.settings.presentation.settings

import com.personal.tmdb.core.navigation.Route

sealed interface SettingsUiEvent {
    data object OnNavigateBack: SettingsUiEvent
    data class OnNavigateTo(val route: Route): SettingsUiEvent
    data class OpenLink(val link: String): SettingsUiEvent
}
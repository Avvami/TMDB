package com.personal.tmdb.detail.presentation.cast

import com.personal.tmdb.core.navigation.Route

sealed interface CastUiEvent {
    data object OnNavigateBack: CastUiEvent
    data class OnNavigateTo(val route: Route): CastUiEvent
}
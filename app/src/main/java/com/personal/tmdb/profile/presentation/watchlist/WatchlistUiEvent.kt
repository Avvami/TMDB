package com.personal.tmdb.profile.presentation.watchlist

import com.personal.tmdb.core.navigation.Route

sealed interface WatchlistUiEvent {
    data object OnNavigateBack: WatchlistUiEvent
    data class OnNavigateTo(val route: Route): WatchlistUiEvent
}
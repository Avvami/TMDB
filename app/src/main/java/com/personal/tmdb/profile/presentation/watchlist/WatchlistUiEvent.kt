package com.personal.tmdb.profile.presentation.watchlist

import com.personal.tmdb.core.domain.util.MediaType
import com.personal.tmdb.core.navigation.Route

sealed interface WatchlistUiEvent {
    data object OnNavigateBack: WatchlistUiEvent
    data class OnNavigateTo(val route: Route): WatchlistUiEvent
    data class GetWatchlist(val mediaType: MediaType, val page: Int): WatchlistUiEvent
    data class SetMediaType(val mediaType: MediaType): WatchlistUiEvent
    data object ShowRecommendations: WatchlistUiEvent
}
package com.personal.tmdb.detail.presentation.episode

import com.personal.tmdb.core.navigation.Route

sealed interface EpisodeDetailsUiEvent {
    data object OnNavigateBack: EpisodeDetailsUiEvent
    data class OnNavigateTo(val route: Route): EpisodeDetailsUiEvent
}
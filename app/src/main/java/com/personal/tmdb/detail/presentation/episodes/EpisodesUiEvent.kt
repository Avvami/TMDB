package com.personal.tmdb.detail.presentation.episodes

import com.personal.tmdb.core.navigation.Route

sealed interface EpisodesUiEvent {
    data object OnNavigateBack: EpisodesUiEvent
    data class OnNavigateTo(val route: Route): EpisodesUiEvent
    data object ChangeOverviewState: EpisodesUiEvent
    data class SetSelectedSeason(val mediaId: Int, val seasonNumber: Int): EpisodesUiEvent
}
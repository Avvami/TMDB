package com.personal.tmdb.detail.presentation.episodes

sealed interface EpisodesUiEvent {
    data object ChangeCollapsedOverview: EpisodesUiEvent
    data object ChangeSeasonDialogState: EpisodesUiEvent
    data class SetSelectedSeason(val mediaId: Int, val seasonNumber: Int): EpisodesUiEvent
}
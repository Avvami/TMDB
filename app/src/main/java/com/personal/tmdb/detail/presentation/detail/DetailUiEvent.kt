package com.personal.tmdb.detail.presentation.detail

sealed interface DetailUiEvent {
    data object ChangeCollapsedOverview: DetailUiEvent
    data object ChangeCollapsedSeasonOverview: DetailUiEvent
    data object ChangeSeasonDropdownState: DetailUiEvent
    data class SetSelectedSeason(val seriesId: Int, val seasonNumber: Int): DetailUiEvent
}
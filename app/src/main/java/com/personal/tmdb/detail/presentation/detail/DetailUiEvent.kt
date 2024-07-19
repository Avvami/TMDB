package com.personal.tmdb.detail.presentation.detail

sealed interface DetailUiEvent {
    data object ChangeCollapsedOverview: DetailUiEvent
    data object ChangeCollapsedSeasonOverview: DetailUiEvent
    data object ChangeSeasonDropdownState: DetailUiEvent
    data class SetSelectedSeason(val seriesId: Int, val seasonNumber: Int): DetailUiEvent
    data class SetSelectedTab(val tabIndex: Int): DetailUiEvent
    data class SetSelectedCountry(val country: String): DetailUiEvent
    data class SetAvailableSearchQuery(val query: String): DetailUiEvent
    data object ChangeAvailableSearchState: DetailUiEvent
    data object ChangeAvailableDialogState: DetailUiEvent
}
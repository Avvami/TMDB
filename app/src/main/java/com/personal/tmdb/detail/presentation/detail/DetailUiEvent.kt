package com.personal.tmdb.detail.presentation.detail

sealed interface DetailUiEvent {
    data object ChangeCollapsedOverview: DetailUiEvent
    data class SetSelectedCountry(val country: String): DetailUiEvent
    data class SetAvailableSearchQuery(val query: String): DetailUiEvent
    data object ChangeAvailableSearchState: DetailUiEvent
    data object ChangeAvailableDialogState: DetailUiEvent
}
package com.personal.tmdb.detail.presentation.detail

import com.personal.tmdb.core.navigation.Route

sealed interface DetailUiEvent {
    data object OnNavigateBack: DetailUiEvent
    data class OnNavigateTo(val route: Route): DetailUiEvent
    data object ChangeCollapsedOverview: DetailUiEvent
    data class SetSelectedCountry(val country: String): DetailUiEvent
    data class SetAvailableSearchQuery(val query: String): DetailUiEvent
    data object ChangeAvailableSearchState: DetailUiEvent
    data object ChangeAvailableDialogState: DetailUiEvent
    data object ChangeShowMoreState: DetailUiEvent
}
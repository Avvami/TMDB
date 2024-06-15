package com.personal.tmdb.detail.presentation

sealed interface DetailUiEvent {
    data object ChangeCollapsedOverview: DetailUiEvent
}
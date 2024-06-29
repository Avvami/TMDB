package com.personal.tmdb.detail.presentation.detail

sealed interface DetailUiEvent {
    data object ChangeCollapsedOverview: DetailUiEvent
}
package com.personal.tmdb.detail.presentation.episode

sealed interface EpisodeDetailsUiEvent {
    data object ChangeCollapsedOverview: EpisodeDetailsUiEvent
}
package com.personal.tmdb.detail.presentation.person

sealed interface PersonUiEvent {
    data object ChangeCollapsedBioState: PersonUiEvent
}
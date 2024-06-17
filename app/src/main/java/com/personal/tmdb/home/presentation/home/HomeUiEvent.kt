package com.personal.tmdb.home.presentation.home

sealed interface HomeUiEvent {
    data class OnSearchQueryChange(val query: String): HomeUiEvent
}
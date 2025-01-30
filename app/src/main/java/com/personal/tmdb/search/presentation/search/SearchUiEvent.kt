package com.personal.tmdb.search.presentation.search

sealed interface SearchUiEvent {
    data class OnSearchQueryChange(val query: String): SearchUiEvent
    data class SetSearchType(val searchType: String): SearchUiEvent
}
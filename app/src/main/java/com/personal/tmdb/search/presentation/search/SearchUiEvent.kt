package com.personal.tmdb.search.presentation.search

sealed interface SearchUiEvent {
    data class OnSearchQueryChange(val query: String, val searchType: String, val page: Int): SearchUiEvent
    data class SetSearchType(val searchType: String): SearchUiEvent
}
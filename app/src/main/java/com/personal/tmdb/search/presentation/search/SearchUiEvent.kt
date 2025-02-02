package com.personal.tmdb.search.presentation.search

import com.personal.tmdb.core.navigation.Route

sealed interface SearchUiEvent {
    data class OnSearchQueryChange(val query: String): SearchUiEvent
    data class SetSearchType(val searchType: String): SearchUiEvent
    data class OnNavigateTo(val route: Route): SearchUiEvent
}
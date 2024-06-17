package com.personal.tmdb.search.presentation.search

import com.personal.tmdb.search.domain.models.SearchInfo

data class SearchState(
    val searchInfo: SearchInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

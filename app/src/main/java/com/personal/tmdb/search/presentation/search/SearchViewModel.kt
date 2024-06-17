package com.personal.tmdb.search.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.search.domain.models.SearchInfo
import com.personal.tmdb.search.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val searchRepository: SearchRepository
): ViewModel() {

    var searchQuery by mutableStateOf(savedStateHandle[C.SEARCH_QUERY] ?: "")
        private set

    var searchType by mutableStateOf(savedStateHandle[C.SEARCH_TYPE] ?: MediaType.MULTI.name.lowercase())
        private set

    var searchState by mutableStateOf(SearchState())
        private set

    private var searchJob: Job? = null

    init {
        searchFor(searchType = searchType, query = searchQuery, page = 1)
    }

    private fun searchFor(searchType: String, query: String, page: Int) {
        if (query.isBlank()) {
            searchState = SearchState()
        } else {
            viewModelScope.launch {
                searchState = searchState.copy(
                    isLoading = true,
                    error = null
                )

                var searchInfo: SearchInfo? = searchState.searchInfo
                var error: String? = null

                searchRepository.searchFor(
                    searchType = searchType,
                    query = query,
                    includeAdult = false,
                    page = page
                ).let { result ->
                    when (result) {
                        is Resource.Error -> {
                            error = result.message
                        }
                        is Resource.Success -> {
                            searchInfo = result.data
                        }
                    }
                }

                searchState = searchState.copy(
                    searchInfo = searchInfo,
                    isLoading = false,
                    error = error
                )
            }
        }
    }

    fun searchUiEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.OnSearchQueryChange -> {
                searchQuery = event.query
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(1000L)
                    searchFor(event.searchType, searchQuery, event.page)
                }
            }
            is SearchUiEvent.SetSearchType -> {
                searchType = event.searchType
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    searchFor(searchType, searchQuery, 1)
                }
            }
        }
    }
}
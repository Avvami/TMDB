package com.personal.tmdb.search.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.core.domain.models.MediaResponseInfo
import com.personal.tmdb.core.presentation.MediaState
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.core.util.TimeWindow
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

    var searchState by mutableStateOf(MediaState())
        private set

    var trendingState by mutableStateOf(MediaState())
        private set

    var popularState by mutableStateOf(MediaState())
        private set

    private var searchJob: Job? = null

    init {
        searchFor(searchType = searchType, query = searchQuery, page = 1)
        getTrendingList()
    }

    private fun searchFor(searchType: String, query: String, page: Int) {
        if (query.isBlank()) {
            searchState = MediaState()
        } else {
            viewModelScope.launch {
                searchState = searchState.copy(
                    isLoading = true,
                    error = null
                )

                var searchInfo: MediaResponseInfo? = searchState.mediaResponseInfo
                var error: String? = null

                searchRepository.searchFor(
                    searchType = searchType,
                    query = query.trim(),
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
                    mediaResponseInfo = searchInfo,
                    isLoading = false,
                    error = error
                )
            }
        }
    }

    private fun getTrendingList(language: String? = null) {
        viewModelScope.launch {
            trendingState = trendingState.copy(
                isLoading = true,
                error = null
            )

            var trending = trendingState.mediaResponseInfo
            var error: String? = null

            searchRepository.getTrendingList(TimeWindow.DAY, language).let { result ->
                when (result) {
                    is Resource.Error -> {
                        error = result.message
                    }
                    is Resource.Success -> {
                        trending = result.data
                    }
                }
            }

            trendingState = trendingState.copy(
                mediaResponseInfo = trending,
                isLoading = false,
                error = error
            )
        }
    }

    private fun getPopularPeopleList(mediaType: String, language: String? = null) {
        viewModelScope.launch {
            popularState = popularState.copy(
                isLoading = true,
                error = null
            )

            var popular = popularState.mediaResponseInfo
            var error: String? = null

            searchRepository.getPopularPeopleList(mediaType, language).let { result ->
                when (result) {
                    is Resource.Error -> {
                        error = result.message
                    }
                    is Resource.Success -> {
                        popular = result.data
                    }
                }
            }

            popularState = popularState.copy(
                mediaResponseInfo = popular,
                isLoading = false,
                error = error
            )
        }
    }

    fun searchUiEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.OnSearchQueryChange -> {
                searchQuery = event.query
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
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
package com.personal.tmdb.search.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.core.presentation.MediaState
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
    private val searchRepository: SearchRepository
): ViewModel() {

    var searchQuery by mutableStateOf("")
        private set

    var searchType by mutableStateOf(MediaType.MULTI.name.lowercase())
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
        getPopularPeopleList(mediaType = MediaType.PERSON.name.lowercase())
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

                searchRepository.searchFor(
                    searchType = searchType,
                    query = query.trim(),
                    includeAdult = false,
                    page = page
                ).let { result ->
                    searchState = when (result) {
                        is Resource.Error -> {
                            searchState.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }

                        is Resource.Success -> {
                            searchState.copy(
                                mediaResponseInfo = result.data,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getTrendingList(language: String? = null) {
        viewModelScope.launch {
            trendingState = trendingState.copy(
                isLoading = true,
                error = null
            )

            searchRepository.getTrendingList(TimeWindow.WEEK, language).let { result ->
                trendingState = when (result) {
                    is Resource.Error -> {
                        trendingState.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                    is Resource.Success -> {
                        trendingState.copy(
                            mediaResponseInfo = result.data,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    private fun getPopularPeopleList(mediaType: String, language: String? = null) {
        viewModelScope.launch {
            popularState = popularState.copy(
                isLoading = true,
                error = null
            )

            searchRepository.getPopularPeopleList(mediaType, language).let { result ->
                popularState = when (result) {
                    is Resource.Error -> {
                        popularState.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }

                    is Resource.Success -> {
                        popularState.copy(
                            mediaResponseInfo = result.data,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun searchUiEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.OnSearchQueryChange -> {
                searchQuery = event.query
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    searchFor(searchType, searchQuery, 1)
                }
            }
            is SearchUiEvent.SetSearchType -> {
                searchType = event.searchType
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    searchFor(searchType, searchQuery, 1)
                }
            }
            is SearchUiEvent.OnNavigateTo -> {}
        }
    }
}
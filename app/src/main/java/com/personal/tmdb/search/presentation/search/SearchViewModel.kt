package com.personal.tmdb.search.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.core.domain.util.MediaType
import com.personal.tmdb.core.domain.util.TimeWindow
import com.personal.tmdb.core.domain.util.onError
import com.personal.tmdb.core.domain.util.onSuccess
import com.personal.tmdb.core.domain.util.toUiText
import com.personal.tmdb.core.presentation.MediaState
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
                    loading = true,
                    errorMessage = null
                )

                searchRepository.searchFor(searchType = searchType, query = query.trim(), includeAdult = false, page = page)
                    .onError { error ->
                        searchState = searchState.copy(
                            loading = false,
                            errorMessage = error.toUiText()
                        )
                    }
                    .onSuccess { result ->
                        searchState = searchState.copy(
                            mediaResponseInfo = result,
                            loading = false
                        )
                    }
            }
        }
    }

    private fun getTrendingList(language: String? = null) {
        viewModelScope.launch {
            trendingState = trendingState.copy(
                loading = true,
                errorMessage = null
            )

            searchRepository.getTrendingList(TimeWindow.WEEK, language)
                .onError { error ->
                    trendingState = trendingState.copy(
                        loading = false,
                        errorMessage = error.toUiText()
                    )
                }
                .onSuccess { result ->
                    trendingState = trendingState.copy(
                        mediaResponseInfo = result,
                        loading = false
                    )
                }
        }
    }

    private fun getPopularPeopleList(mediaType: String, language: String? = null) {
        viewModelScope.launch {
            popularState = popularState.copy(
                loading = true,
                errorMessage = null
            )

            searchRepository.getPopularPeopleList(mediaType, language)
                .onError { error ->
                    popularState = popularState.copy(
                        loading = false,
                        errorMessage = error.toUiText()
                    )
                }
                .onSuccess { result ->
                    popularState = popularState.copy(
                        mediaResponseInfo = result,
                        loading = false
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
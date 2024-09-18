package com.personal.tmdb.home.presentation.home

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.core.util.TimeWindow
import com.personal.tmdb.home.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
): ViewModel() {

    var searchQuery by mutableStateOf("")
        private set

    val snackbarHostState by mutableStateOf(SnackbarHostState())

    var homeState by mutableStateOf(HomeState())
        private set

    init {
        getTrendingList(TimeWindow.DAY)
        getNowPlaying()
    }

    private fun getTrendingList(timeWindow: TimeWindow, language: String? = null) {
        viewModelScope.launch {
            var trending = homeState.trending

            homeRepository.getTrendingList(timeWindow, language).let { result ->
                when(result) {
                    is Resource.Error -> {
                        println(result.message)
                    }
                    is Resource.Success -> {
                        trending = result.data
                    }
                }
            }

            homeState = homeState.copy(
                trending = trending,
                randomMedia = trending?.results?.randomOrNull()
            )
        }
    }

    private fun getNowPlaying(language: String? = null) {
        viewModelScope.launch {
            var nowPlaying = homeState.nowPlaying

            homeRepository.getNowPlaying(language).let { result ->
                when(result) {
                    is Resource.Error -> {
                        println(result.message)
                    }
                    is Resource.Success -> {
                        nowPlaying = result.data
                    }
                }
            }

            homeState = homeState.copy(
                nowPlaying = nowPlaying
            )
        }
    }

    fun homeUiEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.OnSearchQueryChange -> {
                searchQuery = event.query
            }
        }
    }
}
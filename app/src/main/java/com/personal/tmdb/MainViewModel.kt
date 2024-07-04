package com.personal.tmdb

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.core.domain.repository.LocalRepository
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.core.util.TimeWindow
import com.personal.tmdb.home.domain.repository.HomeRepository
import com.personal.tmdb.home.presentation.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val homeRepository: HomeRepository
): ViewModel() {

    var holdSplash by mutableStateOf(true)
        private set

    private val _preferencesState = MutableStateFlow(PreferencesState())
    val preferencesState: StateFlow<PreferencesState> = _preferencesState.asStateFlow()

    var homeState by mutableStateOf(HomeState())
        private set

    init {
        viewModelScope.launch {
            localRepository.getPreferences().collect { preferencesEntity ->
                _preferencesState.update {
                    it.copy(
                        isDark = preferencesEntity.isDark,
                        sessionId = preferencesEntity.sessionId
                    )
                }
                holdSplash = false
            }
        }
        getTrendingList(TimeWindow.DAY)
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

    fun uiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.SetDarkMode -> {
                viewModelScope.launch {
                    localRepository.setDarkMode(event.darkMode)
                }
            }
        }
    }
}
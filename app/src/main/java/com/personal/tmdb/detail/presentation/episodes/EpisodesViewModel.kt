package com.personal.tmdb.detail.presentation.episodes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.personal.tmdb.core.domain.util.MediaType
import com.personal.tmdb.core.domain.util.onError
import com.personal.tmdb.core.domain.util.onSuccess
import com.personal.tmdb.core.domain.util.toUiText
import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.detail.domain.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailRepository: DetailRepository
): ViewModel() {

    private val routeData = savedStateHandle.toRoute<Route.Episodes>()

    private val _seasonState = MutableStateFlow(
        SeasonState(
            mediaId = routeData.mediaId,
            seasonNumber = routeData.seasonNumber
        )
    )
    val seasonState = _seasonState.asStateFlow()

    init {
        getMediaDetails(
            mediaId = routeData.mediaId,
            seasonNumber = routeData.seasonNumber
        )
    }

    private fun getMediaDetails(mediaId: Int, seasonNumber: Int, language: String? = null) {
        viewModelScope.launch {
            _seasonState.update { it.copy(loading = true) }

            detailRepository.getMediaDetail(MediaType.TV.name.lowercase(), mediaId, language)
                .onSuccess { result ->
                    _seasonState.update { it.copy(mediaDetail = result) }
                    getSeasonDetails(mediaId, seasonNumber, language)
                }
                .onError { error ->
                    _seasonState.update {
                        it.copy(
                            loading = false,
                            errorMessage = error.toUiText()
                        )
                    }
                }
        }
    }

    private fun getSeasonDetails(mediaId: Int, seasonNumber: Int, language: String? = null) {
        viewModelScope.launch {
            _seasonState.update {
                it.copy(
                    seasonNumber = seasonNumber,
                    overviewCollapsed = true,
                    loading = true
                )
            }

            detailRepository.getSeasonDetails(mediaId, seasonNumber, language)
                .onError { error ->
                    _seasonState.update {
                        it.copy(
                            loading = false,
                            errorMessage = error.toUiText()
                        )
                    }
                }
                .onSuccess { result ->
                    _seasonState.update {
                        it.copy(
                            seasonInfo = result,
                            loading = false
                        )
                    }
                }
        }
    }

    fun episodesUiEvent(event: EpisodesUiEvent) {
        when(event) {
            EpisodesUiEvent.OnNavigateBack -> {}
            is EpisodesUiEvent.OnNavigateTo -> {}
            EpisodesUiEvent.ChangeOverviewState -> {
                _seasonState.update { it.copy(overviewCollapsed = !it.overviewCollapsed) }
            }
            is EpisodesUiEvent.SetSelectedSeason -> {
                getSeasonDetails(event.mediaId, event.seasonNumber)
            }
        }
    }
}
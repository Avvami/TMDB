package com.personal.tmdb.detail.presentation.episodes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.personal.tmdb.core.domain.util.UiText
import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.Resource
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

            detailRepository.getMediaDetail(MediaType.TV.name.lowercase(), mediaId, language).let { result ->
                when (result) {
                    is Resource.Error -> {
                        _seasonState.update {
                            it.copy(
                                loading = false,
                                errorMessage = UiText.DynamicString(result.message ?: "")
                            )
                        }
                    }
                    is Resource.Success -> {
                        _seasonState.update { it.copy(mediaDetail = result.data) }
                        getSeasonDetails(mediaId, seasonNumber, language)
                    }
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

            detailRepository.getSeasonDetails(mediaId, seasonNumber, language).let { result ->
                when (result) {
                    is Resource.Error -> {
                        _seasonState.update {
                            it.copy(
                                loading = false,
                                errorMessage = UiText.DynamicString(result.message ?: "")
                            )
                        }
                    }
                    is Resource.Success -> {
                        _seasonState.update {
                            it.copy(
                                seasonInfo = result.data,
                                loading = false,
                                errorMessage = UiText.DynamicString(result.message ?: "")
                            )
                        }
                    }
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
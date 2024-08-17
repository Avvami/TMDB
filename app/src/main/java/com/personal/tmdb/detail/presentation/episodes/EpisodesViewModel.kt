package com.personal.tmdb.detail.presentation.episodes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.detail.domain.models.SeasonInfo
import com.personal.tmdb.detail.domain.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailRepository: DetailRepository
): ViewModel() {

    var seasonState by mutableStateOf(SeasonState())
        private set

    var selectedSeasonNumber by mutableIntStateOf(savedStateHandle[C.SEASON_NUMBER] ?: 0)
        private set

    init {
        getMediaDetails(
            mediaId = savedStateHandle[C.MEDIA_ID] ?: 0,
            seasonNumber = selectedSeasonNumber
        )
    }

    private fun getMediaDetails(mediaId: Int, seasonNumber: Int, language: String? = null) {
        viewModelScope.launch {
            seasonState = seasonState.copy(
                isLoading = true
            )

            detailRepository.getMediaDetail(MediaType.TV.name.lowercase(), mediaId, language).let { result ->
                when (result) {
                    is Resource.Error -> {
                        seasonState = seasonState.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                    is Resource.Success -> {
                        seasonState = seasonState.copy(mediaDetail = result.data)
                        getSeasonDetails(mediaId, seasonNumber, language)
                    }
                }
            }
        }
    }

    private fun getSeasonDetails(mediaId: Int, seasonNumber: Int, language: String? = null) {
        viewModelScope.launch {
            seasonState = seasonState.copy(
                isLoading = true
            )

            var seasonInfo: SeasonInfo? = null
            var error: String? = null

            detailRepository.getSeasonDetails(mediaId, seasonNumber, language).let { result ->
                when (result) {
                    is Resource.Error -> {
                        error = result.message
                    }
                    is Resource.Success -> {
                        seasonInfo = result.data
                    }
                }
            }

            seasonState = seasonState.copy(
                seasonInfo = seasonInfo,
                isLoading = false,
                error = error
            )
        }
    }

    var isOverviewCollapsed by mutableStateOf(true)
        private set

    var isSeasonDialogOpen by mutableStateOf(false)
        private set

    fun episodesUiEvent(event: EpisodesUiEvent) {
        when(event) {
            EpisodesUiEvent.ChangeCollapsedOverview -> {
                isOverviewCollapsed = !isOverviewCollapsed
            }
            EpisodesUiEvent.ChangeSeasonDialogState -> {
                isSeasonDialogOpen = !isSeasonDialogOpen
            }
            is EpisodesUiEvent.SetSelectedSeason -> {
                isOverviewCollapsed = true
                selectedSeasonNumber = event.seasonNumber
                getSeasonDetails(event.mediaId, event.seasonNumber)
            }
        }
    }
}
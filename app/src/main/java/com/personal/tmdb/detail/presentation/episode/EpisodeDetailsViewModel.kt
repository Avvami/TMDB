package com.personal.tmdb.detail.presentation.episode

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.core.domain.util.appendToResponse
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.detail.domain.models.EpisodeDetailsInfo
import com.personal.tmdb.detail.domain.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailsViewModel@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailRepository: DetailRepository
): ViewModel() {

    var seasonNumber by mutableIntStateOf(savedStateHandle[C.SEASON_NUMBER] ?: 0)
        private set

    var episodeNumber by mutableIntStateOf(savedStateHandle[C.EPISODE_NUMBER] ?: 0)
        private set

    var episodeDetailsState by mutableStateOf(EpisodeDetailsState())
        private set

    init {
        getEpisodeDetails(
            seriesId = savedStateHandle[C.MEDIA_ID] ?: 0,
            seasonNumber = seasonNumber,
            episodeNumber = episodeNumber,
            appendToResponse = appendToResponse(MediaType.EPISODE.name.lowercase()),
            includeImageLanguage = "null"
        )
    }

    private fun getEpisodeDetails(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int,
        language: String? = null,
        appendToResponse: String? = null,
        includeImageLanguage: String? = null
    ) {
        viewModelScope.launch {
            episodeDetailsState = episodeDetailsState.copy(
                isLoading = true
            )

            var episodeDetails: EpisodeDetailsInfo? = null
            var error: String? = null

            detailRepository.getEpisodeDetails(seriesId, seasonNumber, episodeNumber, language, appendToResponse, includeImageLanguage).let { result ->
                when(result) {
                    is Resource.Error -> {
                        error = result.message
                    }
                    is Resource.Success -> {
                        episodeDetails = result.data
                    }
                }
            }

            episodeDetailsState = episodeDetailsState.copy(
                episodeDetails = episodeDetails,
                isLoading = false,
                error = error
            )
        }
    }

    var isOverviewCollapsed by mutableStateOf(true)
        private set

    fun episodeDetailsUiEvent(event: EpisodeDetailsUiEvent) {
        when(event) {
            EpisodeDetailsUiEvent.ChangeCollapsedOverview -> {
                isOverviewCollapsed = !isOverviewCollapsed
            }
        }
    }
}
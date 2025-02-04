package com.personal.tmdb.detail.presentation.episode

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.personal.tmdb.core.domain.util.UiText
import com.personal.tmdb.core.domain.util.appendToResponse
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
class EpisodeDetailsViewModel@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailRepository: DetailRepository
): ViewModel() {

    private val routeData = savedStateHandle.toRoute<Route.Episode>()

    private val _episodeDetailsState = MutableStateFlow(
        EpisodeDetailsState(
            mediaId = routeData.mediaId,
            seasonNumber = routeData.seasonNumber,
            episodeNumber = routeData.episodeNumber
        )
    )
    val episodeDetailsState = _episodeDetailsState.asStateFlow()

    init {
        getEpisodeDetails(
            mediaId = routeData.mediaId,
            seasonNumber = routeData.seasonNumber,
            episodeNumber = routeData.episodeNumber,
            appendToResponse = appendToResponse(MediaType.EPISODE.name.lowercase()),
            includeImageLanguage = "en,null"
        )
    }

    private fun getEpisodeDetails(
        mediaId: Int,
        seasonNumber: Int,
        episodeNumber: Int,
        language: String? = null,
        appendToResponse: String? = null,
        includeImageLanguage: String? = null
    ) {
        viewModelScope.launch {
            _episodeDetailsState.update { it.copy(loading = true) }

            detailRepository.getEpisodeDetails(mediaId, seasonNumber, episodeNumber, language, appendToResponse, includeImageLanguage).let { result ->
                when(result) {
                    is Resource.Error -> {
                        _episodeDetailsState.update {
                            it.copy(
                                loading = false,
                                errorMessage = UiText.DynamicString(result.message ?: "")
                            )
                        }
                    }
                    is Resource.Success -> {
                        _episodeDetailsState.update {
                            it.copy(
                                episodeDetails = result.data,
                                loading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun episodeDetailsUiEvent(event: EpisodeDetailsUiEvent) {
        when(event) {
            EpisodeDetailsUiEvent.OnNavigateBack -> {}
            is EpisodeDetailsUiEvent.OnNavigateTo -> {}
        }
    }
}
package com.personal.tmdb.detail.presentation.cast

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.personal.tmdb.core.domain.util.UiText
import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.core.domain.util.MediaType
import com.personal.tmdb.core.domain.util.Resource
import com.personal.tmdb.core.domain.util.convertMediaType
import com.personal.tmdb.detail.domain.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CastViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailRepository: DetailRepository
): ViewModel() {

    private val routeData = savedStateHandle.toRoute<Route.Cast>()

    private val _castState = MutableStateFlow(
        CastState(
            mediaId = routeData.mediaId,
            mediaName = routeData.mediaName,
            mediaType = convertMediaType(routeData.mediaType),
            seasonNumber = routeData.seasonNumber,
            episodeNumber = routeData.episodeNumber
        )
    )
    val castState = _castState.asStateFlow()

    init {
        if (routeData.seasonNumber != null && routeData.episodeNumber != null) {
            getEpisodeCast(
                mediaId = routeData.mediaId,
                seasonNumber = routeData.seasonNumber,
                episodeNumber = routeData.episodeNumber
            )
        } else {
            getCast(
                mediaType = convertMediaType(routeData.mediaType),
                mediaId = routeData.mediaId
            )
        }
    }

    private fun getCast(mediaType: MediaType, mediaId: Int, language: String? = null) {
        viewModelScope.launch {
            _castState.update { it.copy(loading = true) }

            val method = if (mediaType == MediaType.TV) "aggregate_credits" else "credits"

            detailRepository.getCast(mediaType.name.lowercase(), mediaId, method, language).let { result ->
                when (result) {
                    is Resource.Error -> {
                        _castState.update {
                            it.copy(
                                loading = false,
                                errorMessage = UiText.DynamicString(result.message ?: "")
                            )
                        }
                    }
                    is Resource.Success -> {
                        _castState.update {
                            it.copy(
                                loading = false,
                                credits = result.data
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getEpisodeCast(mediaId: Int, seasonNumber: Int, episodeNumber: Int, language: String? = null) {
        viewModelScope.launch {
            _castState.update { it.copy(loading = true) }

            detailRepository.getEpisodeCast(mediaId, seasonNumber, episodeNumber, language).let { result ->
                when (result) {
                    is Resource.Error -> {
                        _castState.update {
                            it.copy(
                                loading = false,
                                errorMessage = UiText.DynamicString(result.message ?: "")
                            )
                        }
                    }
                    is Resource.Success -> {
                        _castState.update {
                            it.copy(
                                loading = false,
                                credits = result.data
                            )
                        }
                    }
                }
            }
        }
    }
}

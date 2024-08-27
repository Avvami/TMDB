package com.personal.tmdb.detail.presentation.cast

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.detail.domain.models.CreditsInfo
import com.personal.tmdb.detail.domain.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CastViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailRepository: DetailRepository
): ViewModel() {

    var castState by mutableStateOf(CastState())
        private set

    val mediaName: String = Uri.decode(savedStateHandle[C.MEDIA_NAME] ?: "") ?: ""

    val mediaType: String = savedStateHandle[C.MEDIA_TYPE] ?: ""

    val mediaId: Int = savedStateHandle[C.MEDIA_ID] ?: 0

    private val _seasonNumber: String? = savedStateHandle[C.SEASON_NUMBER]
    val seasonNumber = _seasonNumber?.toIntOrNull()

    private val _episodeNumber: String? = savedStateHandle[C.EPISODE_NUMBER]
    val episodeNumber = _episodeNumber?.toIntOrNull()

    init {
        if (seasonNumber != null && episodeNumber != null) {
            getEpisodeCast(
                mediaId = mediaId,
                seasonNumber = seasonNumber,
                episodeNumber = episodeNumber
            )
        } else {
            getCast(
                mediaType = mediaType,
                mediaId = mediaId
            )
        }
    }

    private fun getCast(mediaType: String, mediaId: Int, language: String? = null) {
        viewModelScope.launch {
            castState = castState.copy(
                isLoading = true
            )

            val method = if (mediaType == "tv") "aggregate_credits" else "credits"
            var credits: CreditsInfo? = null
            var error: String? = null

            detailRepository.getCast(mediaType, mediaId, method, language).let { result ->
                when (result) {
                    is Resource.Error -> {
                        error = result.message
                    }
                    is Resource.Success -> {
                        credits = result.data
                    }
                }
            }

            castState = castState.copy(
                credits = credits,
                isLoading = false,
                error = error
            )
        }
    }

    private fun getEpisodeCast(mediaId: Int, seasonNumber: Int, episodeNumber: Int, language: String? = null) {
        viewModelScope.launch {
            castState = castState.copy(
                isLoading = true
            )

            var credits: CreditsInfo? = null
            var error: String? = null

            detailRepository.getEpisodeCast(mediaId, seasonNumber, episodeNumber, language).let { result ->
                when (result) {
                    is Resource.Error -> {
                        error = result.message
                    }
                    is Resource.Success -> {
                        credits = result.data
                    }
                }
            }

            castState = castState.copy(
                credits = credits,
                isLoading = false,
                error = error
            )
        }
    }
}

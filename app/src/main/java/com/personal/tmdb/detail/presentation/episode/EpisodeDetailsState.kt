package com.personal.tmdb.detail.presentation.episode

import com.personal.tmdb.detail.domain.models.EpisodeDetailsInfo

data class EpisodeDetailsState(
    val episodeDetails: EpisodeDetailsInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
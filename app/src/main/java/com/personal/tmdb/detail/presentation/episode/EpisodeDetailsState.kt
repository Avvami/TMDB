package com.personal.tmdb.detail.presentation.episode

import com.personal.tmdb.core.domain.util.UiText
import com.personal.tmdb.detail.domain.models.EpisodeDetailsInfo

data class EpisodeDetailsState(
    val mediaId: Int = 0,
    val seasonNumber: Int = 0,
    val episodeNumber: Int = 0,
    val episodeDetails: EpisodeDetailsInfo? = null,
    val loading: Boolean = false,
    val errorMessage: UiText? = null
)
package com.personal.tmdb.detail.presentation.cast

import com.personal.tmdb.core.domain.util.UiText
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.detail.domain.models.CreditsInfo

data class CastState(
    val mediaId: Int,
    val mediaName: String,
    val mediaType: MediaType,
    val seasonNumber: Int?,
    val episodeNumber: Int?,
    val credits: CreditsInfo? = null,
    val loading: Boolean = false,
    val errorMessage: UiText? = null
)

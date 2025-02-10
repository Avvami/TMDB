package com.personal.tmdb.core.presentation

import com.personal.tmdb.core.domain.models.MediaResponseInfo
import com.personal.tmdb.core.domain.util.UiText

data class MediaState(
    val mediaResponseInfo: MediaResponseInfo? = null,
    val loading: Boolean = false,
    val errorMessage: UiText? = null
)

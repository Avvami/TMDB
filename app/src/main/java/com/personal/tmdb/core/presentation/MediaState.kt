package com.personal.tmdb.core.presentation

import com.personal.tmdb.core.domain.models.MediaResponseInfo

data class MediaState(
    val mediaResponseInfo: MediaResponseInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

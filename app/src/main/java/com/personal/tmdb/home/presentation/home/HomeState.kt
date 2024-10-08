package com.personal.tmdb.home.presentation.home

import com.personal.tmdb.core.domain.models.MediaInfo
import com.personal.tmdb.core.domain.models.MediaResponseInfo

data class HomeState(
    val randomMedia: MediaInfo? = null,
    val trending: MediaResponseInfo? = null,
    val nowPlaying: MediaResponseInfo? = null,
)
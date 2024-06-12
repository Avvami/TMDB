package com.personal.tmdb.home.presentation.home

import com.personal.tmdb.core.domain.models.MediaInfo

data class HomeState(
    val trending: List<MediaInfo>? = null
)
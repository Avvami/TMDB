package com.personal.tmdb.detail.presentation.detail

import com.personal.tmdb.detail.domain.models.SeasonInfo

data class SeasonState(
    val seasonInfo: SeasonInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

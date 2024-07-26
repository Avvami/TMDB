package com.personal.tmdb.detail.presentation.cast

import com.personal.tmdb.detail.domain.models.CreditsInfo

data class CastState(
    val credits: CreditsInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

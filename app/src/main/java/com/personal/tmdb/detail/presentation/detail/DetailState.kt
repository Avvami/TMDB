package com.personal.tmdb.detail.presentation.detail

import com.personal.tmdb.detail.domain.models.MediaDetailInfo

data class DetailState(
    val mediaDetail: MediaDetailInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

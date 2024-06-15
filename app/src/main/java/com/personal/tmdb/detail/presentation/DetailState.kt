package com.personal.tmdb.detail.presentation

import com.personal.tmdb.detail.domain.models.MediaDetailInfo

data class DetailState(
    val mediaDetail: MediaDetailInfo? = null,
    val error: String? = null
)

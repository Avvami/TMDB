package com.personal.tmdb.detail.domain.models

import com.personal.tmdb.detail.data.models.Cast
import com.personal.tmdb.detail.data.models.Crew

data class CreditsInfo(
    val cast: List<Cast>?,
    val crew: Map<String?, List<Crew>?>?
)

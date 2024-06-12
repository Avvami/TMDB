package com.personal.tmdb.core.domain.models

import com.personal.tmdb.core.util.MediaType

data class MediaInfo(
    val id: Int,
    val mediaType: MediaType,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val voteAverage: Double?
)
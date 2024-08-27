package com.personal.tmdb.core.domain.models

import com.personal.tmdb.core.util.MediaType
import java.time.LocalDate

data class MediaInfo(
    val backdropPath: String?,
    val id: Int,
    val knownFor: List<MediaInfo>? = null,
    val mediaType: MediaType?,
    val name: String?,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: LocalDate?,
    val voteAverage: Float?
)
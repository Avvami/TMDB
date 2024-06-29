package com.personal.tmdb.detail.domain.models

import com.personal.tmdb.core.util.MediaType
import java.time.LocalDate

data class PartInfo(
    val backdropPath: String?,
    val genreIds: List<Int>?,
    val id: Int,
    val mediaType: MediaType,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: LocalDate?,
    val title: String?,
    val voteAverage: Float?
)

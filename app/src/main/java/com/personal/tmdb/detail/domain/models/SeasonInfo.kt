package com.personal.tmdb.detail.domain.models

import java.time.LocalDate

data class SeasonInfo(
    val airDate: LocalDate?,
    val episodes: List<EpisodeInfo>?,
    val idString: String?,
    val id: Int,
    val name: String?,
    val overview: String?,
    val posterPath: String?,
    val seasonNumber: Int,
    val voteAverage: Float?
)

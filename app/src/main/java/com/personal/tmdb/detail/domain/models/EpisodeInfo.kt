package com.personal.tmdb.detail.domain.models

import java.time.LocalDate

data class EpisodeInfo(
    val airDate: LocalDate?,
    val episodeNumber: Int,
    val episodeType: String?,
    val id: Int,
    val name: String?,
    val overview: String?,
    val runtime: Int?,
    val stillPath: String?,
    val voteAverage: Float?
)

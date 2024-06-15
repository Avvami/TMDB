package com.personal.tmdb.detail.domain.models

import com.personal.tmdb.detail.data.models.BelongToCollection
import com.personal.tmdb.detail.data.models.Genre
import com.personal.tmdb.detail.data.models.Network
import com.personal.tmdb.detail.data.models.Season
import java.time.LocalDate

data class MediaDetailInfo(
    val backdropPath: String?,
    val belongsToCollection: BelongToCollection?,
    val genres: List<Genre?>?,
    val id: Int,
    val networks: List<Network?>?,
    val numberOfEpisodes: Int?,
    val numberOfSeasons: Int?,
    val originalName: String?,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: LocalDate,
    val runtime: Int?,
    val seasons: List<Season?>?,
    val tagline: String?,
    val title: String?,
    val voteAverage: Float?,
)

package com.personal.tmdb.detail.domain.models

import com.personal.tmdb.detail.data.models.BelongsToCollection
import com.personal.tmdb.detail.data.models.ContentRatings
import com.personal.tmdb.detail.data.models.CreatedBy
import com.personal.tmdb.detail.data.models.Credits
import com.personal.tmdb.detail.data.models.Genre
import com.personal.tmdb.detail.data.models.Network
import com.personal.tmdb.detail.data.models.ReleaseDates
import com.personal.tmdb.detail.data.models.Season
import java.time.LocalDate

data class MediaDetailInfo(
    val backdropPath: String?,
    val belongsToCollection: BelongsToCollection?,
    val contentRatings: ContentRatings?,
    val createdBy: List<CreatedBy>?,
    val credits: Credits?,
    val genres: List<Genre>?,
    val id: Int,
    val name: String?,
    val networks: List<Network>?,
    val numberOfEpisodes: Int?,
    val numberOfSeasons: Int?,
    val originalName: String?,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: LocalDate?,
    val releaseDates: ReleaseDates?,
    val runtime: Int?,
    val seasons: List<Season>?,
    val tagline: String?,
    val voteAverage: Float?,
)

package com.personal.tmdb.detail.domain.models

import com.personal.tmdb.core.domain.models.MediaResponseInfo
import com.personal.tmdb.detail.data.models.Available
import com.personal.tmdb.detail.data.models.BelongsToCollection
import com.personal.tmdb.detail.data.models.Cast
import com.personal.tmdb.detail.data.models.ContentRatings
import com.personal.tmdb.detail.data.models.CreatedBy
import com.personal.tmdb.detail.data.models.Credits
import com.personal.tmdb.detail.data.models.Genre
import com.personal.tmdb.detail.data.models.Images
import com.personal.tmdb.detail.data.models.Network
import com.personal.tmdb.detail.data.models.ReleaseDates
import com.personal.tmdb.detail.data.models.Season
import java.time.LocalDate

data class MediaDetailInfo(
    val aggregateCredits: Credits?,
    val backdropPath: String?,
    val belongsToCollection: BelongsToCollection?,
    val cast: List<Cast>?,
    val contentRatings: ContentRatings?,
    val createdBy: List<CreatedBy>?,
    val credits: Credits?,
    val genres: List<Genre>?,
    val id: Int,
    val images: Images?,
    val lastEpisodeToAir: EpisodeToAirInfo?,
    val name: String?,
    val networks: List<Network>?,
    val nextEpisodeToAir: EpisodeToAirInfo?,
    val numberOfEpisodes: Int?,
    val numberOfSeasons: Int?,
    val originalLanguage: String?,
    val originalName: String?,
    val overview: String?,
    val posterPath: String?,
    val recommendations: MediaResponseInfo?,
    val releaseDate: LocalDate?,
    val releaseDates: ReleaseDates?,
    val reviews: ReviewsResponseInfo?,
    val runtime: Int?,
    val seasons: List<Season>?,
    val similar: MediaResponseInfo?,
    val tagline: String?,
    val voteAverage: Float?,
    val voteCount: Int?,
    val watchProviders: Map<String, Available>?,
)

package com.personal.tmdb.core.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MediaResponseDto(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<Result>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "genre_ids")
    val genreIds: List<Int>?,
    @Json(name = "id")
    val id: Int,
    @Json(name = "first_air_date")
    val firstAirDate: String?,
    @Json(name = "known_for")
    val knownFor: List<Result>?,
    @Json(name = "media_type")
    val mediaType: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "original_language")
    val originalLanguage: String?,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "profile_path")
    val profilePath: String?,
    @Json(name = "release_date")
    val releaseDate: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "vote_average")
    val voteAverage: Double?
)
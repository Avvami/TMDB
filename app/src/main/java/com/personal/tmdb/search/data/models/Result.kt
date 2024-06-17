package com.personal.tmdb.search.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "first_air_date")
    val firstAirDate: String?,
    @Json(name = "id")
    val id: Int,
    @Json(name = "known_for")
    val knownFor: List<KnownFor?>?,
    @Json(name = "known_for_department")
    val knownForDepartment: String?,
    @Json(name = "media_type")
    val mediaType: String?,
    @Json(name = "name")
    val name: String?,
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
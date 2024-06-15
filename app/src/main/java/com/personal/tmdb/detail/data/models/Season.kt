package com.personal.tmdb.detail.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Season(
    @Json(name = "air_date")
    val airDate: String?,
    @Json(name = "episode_count")
    val episodeCount: Int?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "season_number")
    val seasonNumber: Int?,
    @Json(name = "vote_average")
    val voteAverage: Double?
)
package com.personal.tmdb.detail.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Episode(
    @Json(name = "air_date")
    val airDate: String?,
    @Json(name = "episode_number")
    val episodeNumber: Int,
    @Json(name = "episode_type")
    val episodeType: String?,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String?,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "runtime")
    val runtime: Int?,
    @Json(name = "still_path")
    val stillPath: String?,
    @Json(name = "vote_average")
    val voteAverage: Double?
)
package com.personal.tmdb.detail.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CollectionDto(
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String?,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "parts")
    val parts: List<Part>?,
    @Json(name = "poster_path")
    val posterPath: String?
)
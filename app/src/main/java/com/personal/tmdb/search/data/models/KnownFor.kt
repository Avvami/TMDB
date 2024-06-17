package com.personal.tmdb.search.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class KnownFor(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "title")
    val title: String?,
)
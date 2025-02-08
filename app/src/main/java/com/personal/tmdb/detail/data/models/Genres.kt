package com.personal.tmdb.detail.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Genres(
    @Json(name = "genres")
    val genres: List<Genre>?
)
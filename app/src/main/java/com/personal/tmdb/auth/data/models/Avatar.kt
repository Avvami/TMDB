package com.personal.tmdb.auth.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Avatar(
    @Json(name = "gravatar")
    val gravatar: Gravatar?,
    @Json(name = "tmdb")
    val tmdb: Tmdb?
)
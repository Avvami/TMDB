package com.personal.tmdb.auth.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Gravatar(
    @Json(name = "hash")
    val hash: String?
)
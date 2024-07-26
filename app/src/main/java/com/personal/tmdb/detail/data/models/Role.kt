package com.personal.tmdb.detail.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Role(
    @Json(name = "credit_id")
    val creditId: String,
    @Json(name = "character")
    val character: String,
    @Json(name = "episode_count")
    val episodeCount: Int
)

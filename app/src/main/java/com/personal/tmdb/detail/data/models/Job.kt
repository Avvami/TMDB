package com.personal.tmdb.detail.data.models

import com.squareup.moshi.Json

data class Job(
    @Json(name = "credit_id")
    val creditId: String,
    @Json(name = "job")
    val job: String,
    @Json(name = "episode_count")
    val episodeCount: Int
)

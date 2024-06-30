package com.personal.tmdb.detail.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReleaseDates(
    @Json(name = "results")
    val releaseDatesResults: List<ReleaseDatesResult>?
)
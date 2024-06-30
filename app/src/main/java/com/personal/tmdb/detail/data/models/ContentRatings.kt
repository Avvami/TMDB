package com.personal.tmdb.detail.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContentRatings(
    @Json(name = "results")
    val contentRatingsResults: List<ContentRatingsResult>?
)
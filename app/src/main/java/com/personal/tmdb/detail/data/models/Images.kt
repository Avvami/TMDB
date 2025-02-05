package com.personal.tmdb.detail.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Images(
    @Json(name = "profiles")
    val profiles: List<Image?>?,
    @Json(name = "stills")
    val stills: List<Image?>?,
    @Json(name = "backdrops")
    val backdrops: List<Image?>?,
    @Json(name = "posters")
    val posters: List<Image?>?,
    @Json(name = "logos")
    val logos: List<Image?>?
)
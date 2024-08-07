package com.personal.tmdb.detail.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CombinedCredits(
    @Json(name = "cast")
    val cast: List<CombinedCastCrew>?,
    @Json(name = "crew")
    val crew: List<CombinedCastCrew>?
)
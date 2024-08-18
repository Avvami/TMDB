package com.personal.tmdb.detail.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Translation(
    @Json(name = "data")
    val data: Data?,
    @Json(name = "english_name")
    val englishName: String?,
    @Json(name = "iso_3166_1")
    val iso31661: String?,
    @Json(name = "iso_639_1")
    val iso6391: String?,
    @Json(name = "name")
    val name: String?
)
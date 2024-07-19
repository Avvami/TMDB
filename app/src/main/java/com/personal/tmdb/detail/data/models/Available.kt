package com.personal.tmdb.detail.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Available(
    @Json(name = "link")
    val link: String,
    @Json(name = "ads")
    val ads: List<Provider>? = null,
    @Json(name = "flatrate")
    val flatrate: List<Provider>? = null,
    @Json(name = "free")
    val free: List<Provider>? = null,
    @Json(name = "buy")
    val buy: List<Provider>? = null,
    @Json(name = "rent")
    val rent: List<Provider>? = null
)
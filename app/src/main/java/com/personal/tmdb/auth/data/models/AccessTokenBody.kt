package com.personal.tmdb.auth.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccessTokenBody(
    @Json(name = "access_token")
    val accessToken: String
)

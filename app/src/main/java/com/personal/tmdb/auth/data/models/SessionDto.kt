package com.personal.tmdb.auth.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SessionDto(
    @Json(name = "success")
    val success: Boolean,
    @Json(name = "session_id")
    val sessionId: String?
)

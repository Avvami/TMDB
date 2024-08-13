package com.personal.tmdb.auth.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RequestTokenDto(
    @Json(name = "status_message")
    val statusMessage: String,
    @Json(name = "request_token")
    val requestToken: String,
    @Json(name = "success")
    val success: Boolean,
    @Json(name = "status_code")
    val statusCode: Int
)
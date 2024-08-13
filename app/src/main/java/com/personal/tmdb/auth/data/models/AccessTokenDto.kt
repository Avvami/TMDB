package com.personal.tmdb.auth.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccessTokenDto(
    @Json(name = "account_id")
    val accountId: String?,
    @Json(name = "access_token")
    val accessToken: String?,
    @Json(name = "success")
    val success: Boolean,
    @Json(name = "status_message")
    val statusMessage: String,
    @Json(name = "status_code")
    val statusCode: Int
)

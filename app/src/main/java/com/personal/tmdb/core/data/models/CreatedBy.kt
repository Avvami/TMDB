package com.personal.tmdb.core.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreatedBy(
    @Json(name = "avatar_path")
    val avatarPath: String?,
    @Json(name = "gravatar_hash")
    val gravatarHash: String?,
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String?,
    @Json(name = "username")
    val username: String?
)
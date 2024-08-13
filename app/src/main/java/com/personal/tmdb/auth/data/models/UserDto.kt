package com.personal.tmdb.auth.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "avatar")
    val avatar: Avatar,
    @Json(name = "id")
    val id: Int,
    @Json(name = "include_adult")
    val includeAdult: Boolean,
    @Json(name = "iso_3166_1")
    val iso31661: String,
    @Json(name = "iso_639_1")
    val iso6391: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "username")
    val username: String
)
package com.personal.tmdb.detail.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreatedBy(
    @Json(name = "credit_id")
    val creditId: String?,
    @Json(name = "gender")
    val gender: Int?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "original_name")
    val originalName: String?,
    @Json(name = "profile_path")
    val profilePath: String?
)
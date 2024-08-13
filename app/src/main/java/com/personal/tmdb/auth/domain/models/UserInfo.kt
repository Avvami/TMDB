package com.personal.tmdb.auth.domain.models

data class UserInfo(
    val accountId: Int,
    val gravatarAvatarPath: String?,
    val tmdbAvatarPath: String?,
    val iso31661: String,
    val iso6391: String,
    val name: String,
    val username: String
)

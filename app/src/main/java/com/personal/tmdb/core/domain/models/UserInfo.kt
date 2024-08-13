package com.personal.tmdb.core.domain.models

data class UserInfo(
    val accountId: Int,
    val tmdbAvatarPath: String?,
    val gravatarAvatarPath: String?,
    val iso6391: String,
    val name: String,
    val username: String
)

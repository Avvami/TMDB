package com.personal.tmdb.core.domain.models

data class User(
    val accessToken: String? = null,
    val accountId: Int? = null,
    val accountObjectId: String? = null,
    val sessionId: String? = null,
    val gravatarAvatarPath: String? = null,
    val tmdbAvatarPath: String? = null,
    val iso6391: String? = null,
    val iso31661: String? = null,
    val name: String? = null,
    val includeAdult: Boolean? = null,
    val username: String? = null
)

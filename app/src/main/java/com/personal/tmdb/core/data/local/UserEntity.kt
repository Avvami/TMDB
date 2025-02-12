package com.personal.tmdb.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey val id: Int = 0,
    val accessToken: String?,
    val accountId: Int?,
    val accountObjectId: String?,
    val sessionId: String?,
    val gravatarAvatarPath: String?,
    val tmdbAvatarPath: String?,
    val iso6391: String?,
    val iso31661: String?,
    val name: String?,
    val includeAdult: Boolean?,
    val username: String?
)
package com.personal.tmdb.core.data.mappers

import com.personal.tmdb.core.data.local.UserEntity
import com.personal.tmdb.core.domain.models.User

fun UserEntity.toUser(): User {
    return User(
        accessToken, accountId, accountObjectId, sessionId, gravatarAvatarPath, tmdbAvatarPath, iso6391, iso31661, name, includeAdult, username
    )
}

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        accessToken = accessToken,
        accountId = accountId,
        accountObjectId = accountObjectId,
        sessionId = sessionId,
        gravatarAvatarPath = gravatarAvatarPath,
        tmdbAvatarPath = tmdbAvatarPath,
        iso6391 = iso6391,
        iso31661 = iso31661,
        name = name,
        includeAdult = includeAdult,
        username = username
    )
}
package com.personal.tmdb.auth.data.mappers

import com.personal.tmdb.auth.data.models.UserDto
import com.personal.tmdb.auth.domain.models.UserInfo

fun UserDto.toUserInfo(): UserInfo {
    return UserInfo(
        accountId = id,
        gravatarAvatarPath = avatar.gravatar?.hash,
        tmdbAvatarPath = avatar.tmdb?.avatarPath,
        iso31661 = iso31661,
        iso6391 = iso6391,
        name = name,
        username = username
    )
}
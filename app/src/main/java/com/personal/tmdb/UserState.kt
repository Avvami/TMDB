package com.personal.tmdb

import com.personal.tmdb.auth.domain.models.UserInfo
import com.personal.tmdb.core.domain.util.UiText

data class UserState(
    val loading: Boolean = false,
    val userInfo: UserInfo? = null,
    val errorMessage: UiText? = null,
    val requestToken: String? = null,
    val accessToken: String? = null,
    val sessionId: String? = null,
    val accountId: String? = null
)

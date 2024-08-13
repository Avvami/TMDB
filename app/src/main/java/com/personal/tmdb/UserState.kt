package com.personal.tmdb

import com.personal.tmdb.auth.domain.models.UserInfo

data class UserState(
    val isLoading: Boolean = false,
    val userInfo: UserInfo? = null,
    val error: String? = null,
    val showSnackDone: Boolean = false,
    val requestToken: String? = null,
    val accessToken: String? = null,
    val sessionId: String? = null,
    val accountId: String? = null
)

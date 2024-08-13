package com.personal.tmdb

import com.personal.tmdb.core.domain.models.UserInfo

data class UserState(
    val isLoading: Boolean = false,
    val userInfo: UserInfo? = null,
    val error: String? = null,
    val showSnackDone: Boolean = false,
    val requestToken: String? = null,
    val accessToken: String? = null,
    val sessionId: String? = null,
    val v4Id: String? = null
)

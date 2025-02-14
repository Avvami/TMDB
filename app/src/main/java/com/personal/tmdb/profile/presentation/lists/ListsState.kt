package com.personal.tmdb.profile.presentation.lists

import com.personal.tmdb.core.domain.models.ListsResponseInfo
import com.personal.tmdb.core.domain.util.UiText

data class ListsState(
    val loading: Boolean = false,
    val lists: ListsResponseInfo? = null,
    val errorMessage: UiText? = null
)

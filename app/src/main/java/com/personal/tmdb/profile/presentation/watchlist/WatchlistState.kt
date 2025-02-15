package com.personal.tmdb.profile.presentation.watchlist

import com.personal.tmdb.core.domain.models.MediaResponseInfo
import com.personal.tmdb.core.domain.util.MediaType
import com.personal.tmdb.core.domain.util.UiText

data class WatchlistState(
    val loading: Boolean = false,
    val watchlist: MediaResponseInfo? = null,
    val mediaType: MediaType = MediaType.TV,
    val showRecommendations: Boolean = false,
    val errorMessage: UiText? = null
)

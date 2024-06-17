package com.personal.tmdb.search.domain.models

import com.personal.tmdb.core.domain.models.MediaInfo

data class SearchInfo(
    val page: Int,
    val results: List<MediaInfo>,
    val totalPages: Int,
    val totalResults: Int
)
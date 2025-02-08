package com.personal.tmdb.detail.data.mappers

import com.personal.tmdb.detail.data.models.Genres
import com.personal.tmdb.detail.domain.models.GenresInfo

fun Genres.toGenresInfo(): GenresInfo {
    return GenresInfo(
        genres = genres ?: emptyList()
    )
}
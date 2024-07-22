package com.personal.tmdb.detail.domain.models

import com.personal.tmdb.core.domain.models.MediaInfo

data class CollectionInfo(
    val averageRating: Float,
    val backdropPath: String?,
    val id: Int,
    val name: String?,
    val overview: String?,
    val parts: List<MediaInfo>?,
    val posterPath: String?
)

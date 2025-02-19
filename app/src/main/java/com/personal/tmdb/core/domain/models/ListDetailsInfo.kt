package com.personal.tmdb.core.domain.models

import com.personal.tmdb.core.data.models.CreatedBy
import com.personal.tmdb.core.domain.util.UiText

data class ListDetailsInfo(
    val averageRating: Float?,
    val backdropPath: String?,
    val createdBy: CreatedBy?,
    val description: UiText,
    val id: Int,
    val iso31661: String?,
    val iso6391: String?,
    val itemCount: Int,
    val name: UiText,
    val page: Int,
    val posterPath: String?,
    val public: Boolean,
    val results: List<MediaInfo>?,
    val revenue: Int?,
    val runtime: Int?,
    val sortBy: String?,
    val totalPages: Int,
    val totalResults: Int
)
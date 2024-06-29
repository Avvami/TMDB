package com.personal.tmdb.detail.domain.models

data class CollectionInfo(
    val backdropPath: String?,
    val id: Int,
    val name: String?,
    val overview: String?,
    val parts: List<PartInfo>?,
    val posterPath: String?
)

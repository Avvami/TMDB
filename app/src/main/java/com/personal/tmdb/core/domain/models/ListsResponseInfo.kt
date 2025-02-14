package com.personal.tmdb.core.domain.models

data class ListsResponseInfo(
    val page: Int,
    val results: List<ListInfo>,
    val totalPages: Int,
    val totalResults: Int
)

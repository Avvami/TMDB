package com.personal.tmdb.search.domain.models

data class SearchInfo(
    val page: Int,
    val results: List<ResultInfo?>,
    val totalPages: Int,
    val totalResults: Int
)
package com.personal.tmdb.detail.domain.models

data class ReviewsResponseInfo(
    val page: Int,
    val results: List<ReviewInfo>,
    val totalPages: Int,
    val totalResults: Int
)

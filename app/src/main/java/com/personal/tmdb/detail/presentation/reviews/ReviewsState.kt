package com.personal.tmdb.detail.presentation.reviews

import com.personal.tmdb.detail.domain.models.ReviewsResponseInfo

data class ReviewsState(
    val isLoading: Boolean = false,
    val reviews: ReviewsResponseInfo? = null,
    val error: String? = null
)

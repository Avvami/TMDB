package com.personal.tmdb.detail.presentation.reviews

import com.personal.tmdb.core.domain.util.UiText
import com.personal.tmdb.detail.domain.models.ReviewsResponseInfo

data class ReviewsState(
    val loading: Boolean = false,
    val reviews: ReviewsResponseInfo? = null,
    val selectedReviewIndex: Int = 0,
    val showSelectedReview: Boolean = false,
    val errorMessage: UiText? = null
)

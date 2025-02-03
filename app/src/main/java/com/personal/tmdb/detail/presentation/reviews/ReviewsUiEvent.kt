package com.personal.tmdb.detail.presentation.reviews

sealed interface ReviewsUiEvent {
    data object OnNavigateBack: ReviewsUiEvent
    data class SetSelectedReview(val reviewIndex: Int, val show: Boolean): ReviewsUiEvent
}
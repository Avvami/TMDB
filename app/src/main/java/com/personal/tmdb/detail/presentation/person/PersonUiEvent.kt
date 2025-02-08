package com.personal.tmdb.detail.presentation.person

import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.core.util.MediaType

sealed interface PersonUiEvent {
    data object OnNavigateBack: PersonUiEvent
    data class OnNavigateTo(val route: Route): PersonUiEvent
    data class SortPersonCredits(val department: String? = "", val mediaType: MediaType? = null): PersonUiEvent
}
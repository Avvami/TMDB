package com.personal.tmdb.detail.presentation.collection

import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.detail.domain.util.CollectionSortType

sealed interface CollectionUiEvent {
    data object OnNavigateBack: CollectionUiEvent
    data class OnNavigateTo(val route: Route): CollectionUiEvent
    data class SetSortType(val sortType: CollectionSortType?): CollectionUiEvent
}
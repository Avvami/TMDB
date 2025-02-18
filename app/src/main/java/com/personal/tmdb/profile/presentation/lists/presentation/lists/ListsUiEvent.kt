package com.personal.tmdb.profile.presentation.lists.presentation.lists

import com.personal.tmdb.core.navigation.Route

sealed interface ListsUiEvent {
    data object OnNavigateBack: ListsUiEvent
    data class OnNavigateTo(val route: Route): ListsUiEvent
    data class GetLists(val page: Int): ListsUiEvent
}
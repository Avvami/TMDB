package com.personal.tmdb.profile.presentation.profile

import com.personal.tmdb.core.navigation.Route

sealed interface ProfileUiEvent {
    data class OnNavigateTo(val route: Route): ProfileUiEvent
    data object CreateRequestToken: ProfileUiEvent
    data object DropRequestToken: ProfileUiEvent
}
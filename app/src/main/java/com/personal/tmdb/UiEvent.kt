package com.personal.tmdb

sealed interface UiEvent {
    data object CreateRequestToken: UiEvent
    data object DropRequestToken: UiEvent
    data object SignInUser: UiEvent
    data object SignOut: UiEvent
}
package com.personal.tmdb

sealed interface UiEvent {
    data class SetTheme(val darkTheme: Boolean?): UiEvent
    data object CreateRequestToken: UiEvent
    data object DropRequestToken: UiEvent
    data object SignInUser: UiEvent
    data object DropSnackDone: UiEvent
    data object DropError: UiEvent
}
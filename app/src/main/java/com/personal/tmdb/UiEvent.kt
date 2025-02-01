package com.personal.tmdb

sealed interface UiEvent {
    data class SetTheme(val darkTheme: Boolean?): UiEvent
    data object CreateRequestToken: UiEvent
    data object DropRequestToken: UiEvent
    data object SignInUser: UiEvent
    data class SetUseCards(val userCards: Boolean): UiEvent
    data class SetShowTitle(val showTitle: Boolean): UiEvent
    data class SetShowVoteAverage(val showVoteAverage: Boolean): UiEvent
    data class SetCorners(val corners: Int): UiEvent
}
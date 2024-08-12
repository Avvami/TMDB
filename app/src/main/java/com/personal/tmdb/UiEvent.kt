package com.personal.tmdb

sealed interface UiEvent {
    data class SetTheme(val darkTheme: Boolean?): UiEvent
}
package com.personal.tmdb

sealed interface UiEvent {
    data class SetDarkMode(val darkMode: Boolean): UiEvent
}
package com.personal.tmdb.detail.presentation.detail

data class AvailableState(
    val isDialogShown: Boolean = false,
    val isSearchActive: Boolean = false,
    val selectedCountry: String = ""
)

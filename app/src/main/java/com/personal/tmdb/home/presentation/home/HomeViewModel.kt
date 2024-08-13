package com.personal.tmdb.home.presentation.home

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {

    var searchQuery by mutableStateOf("")
        private set

    val snackbarHostState by mutableStateOf(SnackbarHostState())

    fun homeUiEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.OnSearchQueryChange -> {
                searchQuery = event.query
            }
        }
    }
}
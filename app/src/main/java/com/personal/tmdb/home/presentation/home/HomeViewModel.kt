package com.personal.tmdb.home.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {

    var searchQuery by mutableStateOf("")
        private set

    fun homeUiEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.OnSearchQueryChange -> {
                searchQuery = event.query
            }
        }
    }
}
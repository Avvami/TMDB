package com.personal.tmdb.profile.presentation.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.core.domain.repository.PreferencesRepository
import com.personal.tmdb.core.domain.repository.UserRepository
import com.personal.tmdb.core.domain.util.MediaType
import kotlinx.coroutines.launch

class WatchlistViewModel(
    private val preferencesRepository: PreferencesRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private fun getWatchlist(
        mediaType: MediaType,
        page: Int
    ) {
        viewModelScope.launch {}
    }
}
package com.personal.tmdb.settings.presentation.appearance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.core.domain.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppearanceViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
): ViewModel() {

    fun appearanceUiEvent(event: AppearanceUiEvent) {
        when (event) {
            AppearanceUiEvent.OnNavigateBack -> {}
            is AppearanceUiEvent.SetAdditionalNavItem -> {
                viewModelScope.launch {
                    preferencesRepository.setAdditionalNavigationItem(event.item.name.lowercase())
                }
            }
            is AppearanceUiEvent.SetShowTitle -> {
                viewModelScope.launch {
                    preferencesRepository.setShowTitle(event.showTitle)
                }
            }
            is AppearanceUiEvent.SetShowVoteAverage -> {
                viewModelScope.launch {
                    preferencesRepository.setShowVoteAverage(event.showVoteAverage)
                }
            }
            is AppearanceUiEvent.SetTheme -> {
                viewModelScope.launch {
                    preferencesRepository.setTheme(event.darkTheme)
                }
            }
        }
    }
}
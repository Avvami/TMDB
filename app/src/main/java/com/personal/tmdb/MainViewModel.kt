package com.personal.tmdb

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.core.domain.repository.LocalRepository
import com.personal.tmdb.core.presentation.preferences.PreferencesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localRepository: LocalRepository
): ViewModel() {

    var holdSplash by mutableStateOf(true)
        private set

    private val _preferencesState = MutableStateFlow(PreferencesState())
    val preferencesState: StateFlow<PreferencesState> = _preferencesState.asStateFlow()

    init {
        viewModelScope.launch {
            localRepository.getPreferences().collect { preferencesEntity ->
                _preferencesState.update {
                    it.copy(
                        isDark = preferencesEntity.isDark,
                        sessionId = preferencesEntity.sessionId
                    )
                }
                holdSplash = false
            }
        }
    }

    fun uiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.SetDarkMode -> {
                viewModelScope.launch {
                    localRepository.setDarkMode(event.darkMode)
                }
            }
        }
    }
}
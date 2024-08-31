package com.personal.tmdb.core.presentation.image

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageViewerViewModel @Inject constructor(): ViewModel() {

    var showGridView by mutableStateOf(false)
        private set

    var hideUi by mutableStateOf(false)
        private set

    fun imageViewerUiEvent(event: ImageViewerUiEvent) {
        when(event) {
            ImageViewerUiEvent.ChangeShowGridView -> {
                showGridView = !showGridView
            }
            ImageViewerUiEvent.ChangeHideUi -> {
                hideUi = !hideUi
            }
        }
    }
}
package com.personal.tmdb.core.presentation.image

sealed interface ImageViewerUiEvent {
    data object ChangeShowGridView: ImageViewerUiEvent
    data object ChangeHideUi: ImageViewerUiEvent
}
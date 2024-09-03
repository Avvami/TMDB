package com.personal.tmdb.detail.presentation.image

sealed interface ImageViewerUiEvent {
    data object ChangeShowGridView: ImageViewerUiEvent
    data object ChangeHideUi: ImageViewerUiEvent
}
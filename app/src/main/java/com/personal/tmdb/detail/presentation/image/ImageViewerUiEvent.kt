package com.personal.tmdb.detail.presentation.image

import com.personal.tmdb.detail.domain.util.ImageType

sealed interface ImageViewerUiEvent {
    data object OnNavigateBack: ImageViewerUiEvent
    data object ChangeShowGridViewState: ImageViewerUiEvent
    data object ChangeHideUiState: ImageViewerUiEvent
    data class SetImageType(val imageType: ImageType): ImageViewerUiEvent
}
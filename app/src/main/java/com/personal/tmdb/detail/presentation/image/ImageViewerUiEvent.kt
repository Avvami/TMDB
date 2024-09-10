package com.personal.tmdb.detail.presentation.image

import com.personal.tmdb.detail.domain.util.ImageType

sealed interface ImageViewerUiEvent {
    data object ChangeShowGridView: ImageViewerUiEvent
    data object ChangeHideUi: ImageViewerUiEvent
    data class SetImageType(val type: ImageType): ImageViewerUiEvent
}
package com.personal.tmdb.detail.presentation.image

import com.personal.tmdb.core.domain.util.UiText
import com.personal.tmdb.detail.data.models.Image
import com.personal.tmdb.detail.data.models.Images
import com.personal.tmdb.detail.domain.util.ImageType

data class ImagesState(
    val initialPage: Int?,
    val imageType: ImageType,
    val state: Images? = null,
    val imagesByType: List<Image?>? = null,
    val loading: Boolean = false,
    val errorMessage: UiText? = null,
    val showGridView: Boolean = initialPage == null,
    val hideUi: Boolean = false
)

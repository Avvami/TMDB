package com.personal.tmdb.detail.presentation.image

import com.personal.tmdb.detail.data.models.Image
import com.personal.tmdb.detail.data.models.Images

data class ImagesState(
    val state: Images? = null,
    val images: List<Image?>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

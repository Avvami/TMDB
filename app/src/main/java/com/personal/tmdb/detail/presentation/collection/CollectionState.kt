package com.personal.tmdb.detail.presentation.collection

import com.personal.tmdb.core.domain.models.MediaInfo
import com.personal.tmdb.detail.domain.models.CollectionInfo

data class CollectionState(
    val collectionInfo: CollectionInfo? = null,
    val originalParts: List<MediaInfo>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

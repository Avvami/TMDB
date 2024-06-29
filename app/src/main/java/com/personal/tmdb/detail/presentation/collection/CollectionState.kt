package com.personal.tmdb.detail.presentation.collection

import com.personal.tmdb.detail.domain.models.CollectionInfo

data class CollectionState(
    val collectionInfo: CollectionInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

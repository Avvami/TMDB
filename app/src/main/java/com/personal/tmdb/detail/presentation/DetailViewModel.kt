package com.personal.tmdb.detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class DetailViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    init {
        val f = savedStateHandle["mediaId"] ?: 0
        val type = savedStateHandle["mediaTYpe"] ?: ""
    }

}
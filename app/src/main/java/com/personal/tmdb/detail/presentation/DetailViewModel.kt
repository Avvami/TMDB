package com.personal.tmdb.detail.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.detail.domain.models.MediaDetailInfo
import com.personal.tmdb.detail.domain.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailRepository: DetailRepository
): ViewModel() {

    var detailState by mutableStateOf(DetailState())
        private set

    init {
        getMediaDetails(
            mediaType = savedStateHandle[C.MEDIA_TYPE] ?: "",
            mediaId = savedStateHandle[C.MEDIA_ID] ?: 0,
            appendToResponse = "credits"
        )
    }

    private fun getMediaDetails(mediaType: String, mediaId: Int, language: String? = null, appendToResponse: String? = null) {
        viewModelScope.launch {
            var mediaDetail: MediaDetailInfo? = null

            detailRepository.getMediaDetail(mediaType, mediaId, language, appendToResponse).let { result ->
                when (result) {
                    is Resource.Error -> {
                        println(result.message)
                    }
                    is Resource.Success -> {
                        mediaDetail = result.data
                    }
                }
            }

            detailState = detailState.copy(
                mediaDetail = mediaDetail
            )
        }
    }

    var isOverviewCollapsed by mutableStateOf(true)
        private set

    fun detailUiEvent(event: DetailUiEvent) {
        when (event) {
            DetailUiEvent.ChangeCollapsedOverview -> {
                isOverviewCollapsed = !isOverviewCollapsed
            }
        }
    }
}
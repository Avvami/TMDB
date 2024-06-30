package com.personal.tmdb.detail.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.detail.domain.models.CollectionInfo
import com.personal.tmdb.detail.domain.models.MediaDetailInfo
import com.personal.tmdb.detail.domain.repository.DetailRepository
import com.personal.tmdb.core.domain.util.appendToResponse
import com.personal.tmdb.detail.presentation.collection.CollectionState
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

    var collectionState by mutableStateOf(CollectionState())
        private set

    init {
        getMediaDetails(
            mediaType = savedStateHandle[C.MEDIA_TYPE] ?: "",
            mediaId = savedStateHandle[C.MEDIA_ID] ?: 0,
            appendToResponse = appendToResponse(savedStateHandle[C.MEDIA_TYPE] ?: "")
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
            mediaDetail?.belongsToCollection?.let { collection ->
                getCollection(collectionId = collection.id)
            }

            detailState = detailState.copy(
                mediaDetail = mediaDetail
            )
        }
    }

    private fun getCollection(collectionId: Int, language: String? = null) {
        viewModelScope.launch {
            collectionState = collectionState.copy(
                isLoading = true
            )

            var collectionInfo: CollectionInfo? = null

            detailRepository.getCollection(collectionId, language).let { result ->
                when (result) {
                    is Resource.Error -> {
                        println(result.message)
                    }
                    is Resource.Success -> {
                        collectionInfo = result.data
                    }
                }
            }

            collectionState = collectionState.copy(
                collectionInfo = collectionInfo,
                isLoading = false
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
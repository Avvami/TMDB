package com.personal.tmdb.detail.presentation.reviews

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.detail.domain.models.ReviewsResponseInfo
import com.personal.tmdb.detail.domain.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailRepository: DetailRepository
): ViewModel() {

    var reviewsState by mutableStateOf(ReviewsState())
        private set

    val mediaType: String = savedStateHandle[C.MEDIA_TYPE] ?: ""

    val mediaId: Int = savedStateHandle[C.MEDIA_ID] ?: 0

    init {
        getReviews(
            mediaType = mediaType,
            mediaId = mediaId
        )
    }

    private fun getReviews(
        mediaType: String,
        mediaId: Int,
        page: Int = 1,
        language: String? = null
    ) {
        viewModelScope.launch {
            reviewsState = reviewsState.copy(
                isLoading = true
            )

            var reviews: ReviewsResponseInfo? = null
            var error: String? = null

            detailRepository.getReviews(mediaType, mediaId, page, language).let { result ->
                when(result) {
                    is Resource.Error -> {
                        error = result.message
                    }
                    is Resource.Success -> {
                        reviews = result.data
                    }
                }
            }

            reviewsState = reviewsState.copy(
                isLoading = false,
                reviews = reviews,
                error = error
            )
        }
    }
}
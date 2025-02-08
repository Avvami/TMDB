package com.personal.tmdb.detail.presentation.reviews

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.personal.tmdb.core.domain.util.UiText
import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.core.domain.util.Resource
import com.personal.tmdb.detail.domain.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailRepository: DetailRepository
): ViewModel() {

    private val routeData = savedStateHandle.toRoute<Route.Reviews>()

    private val _reviewsState = MutableStateFlow(
        ReviewsState(
            selectedReviewIndex = routeData.selectedReviewIndex ?: 0,
            showSelectedReview = routeData.selectedReviewIndex != null
        )
    )
    val reviewsState = _reviewsState.asStateFlow()

    init {
        getReviews(
            mediaType = routeData.mediaType,
            mediaId = routeData.mediaId
        )
    }

    private fun getReviews(
        mediaType: String,
        mediaId: Int,
        page: Int = 1,
        language: String? = null
    ) {
        viewModelScope.launch {
            _reviewsState.update { it.copy(loading = true) }

            detailRepository.getReviews(mediaType, mediaId, page, language).let { result ->
                when(result) {
                    is Resource.Error -> {
                        _reviewsState.update {
                            it.copy(
                                loading = false,
                                errorMessage = UiText.DynamicString(result.message ?: "")
                            )
                        }
                    }
                    is Resource.Success -> {
                        _reviewsState.update {
                            it.copy(
                                loading = false,
                                reviews = result.data
                            )
                        }
                    }
                }
            }
        }
    }

    fun reviewsUiEvent(event: ReviewsUiEvent) {
        when (event) {
            ReviewsUiEvent.OnNavigateBack -> {}
            is ReviewsUiEvent.SetSelectedReview -> {
                _reviewsState.update {
                    it.copy(
                        selectedReviewIndex = event.reviewIndex,
                        showSelectedReview = event.show
                    )
                }
            }
        }
    }
}
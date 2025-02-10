package com.personal.tmdb.detail.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.personal.tmdb.core.domain.util.appendToResponse
import com.personal.tmdb.core.domain.util.convertMediaType
import com.personal.tmdb.core.domain.util.onError
import com.personal.tmdb.core.domain.util.onSuccess
import com.personal.tmdb.core.domain.util.toUiText
import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.detail.domain.repository.DetailRepository
import com.personal.tmdb.detail.presentation.collection.CollectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailRepository: DetailRepository
): ViewModel() {

    private val routeData = savedStateHandle.toRoute<Route.Detail>()

    private val _detailState = MutableStateFlow(
        DetailState(
            mediaType = convertMediaType(routeData.mediaType),
            mediaId = routeData.mediaId
        )
    )
    val detailState = _detailState.asStateFlow()

    var collectionState by mutableStateOf(CollectionState())
        private set

    var availableState by mutableStateOf(AvailableState())
        private set

    private val _availableSearchQuery = MutableStateFlow("")
    var availableSearchQuery = _availableSearchQuery.asStateFlow()

    private val _availableCountries = MutableStateFlow(detailState.value.details?.watchProviders?.keys)
    val availableCountries = availableSearchQuery.combine(_availableCountries) { query, countries ->
        if (query.isBlank()) {
            countries
        } else {
            countries?.filter {
                it.contains(query, ignoreCase = true)
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _availableCountries.value
    )

    init {
        getMediaDetails(
            mediaType = routeData.mediaType,
            mediaId = routeData.mediaId,
            appendToResponse = appendToResponse(routeData.mediaType),
            includeImageLanguage = "en,null"
        )
    }

    private fun getMediaDetails(
        mediaType: String,
        mediaId: Int,
        language: String? = null,
        appendToResponse: String? = null,
        includeImageLanguage: String? = null
    ) {
        viewModelScope.launch {
            _detailState.update { it.copy(loading = true) }

            detailRepository.getMediaDetail(mediaType, mediaId, language, appendToResponse, includeImageLanguage)
                .onError { error ->
                    _detailState.update {
                        it.copy(
                            loading = false,
                            errorMessage = error.toUiText()
                        )
                    }
                }
                .onSuccess { result ->
                    result.belongsToCollection?.let { collection ->
                        getCollection(collectionId = collection.id)
                    }
                    if (result.watchProviders != null) {
                        _availableCountries.value = result.watchProviders.keys
                        availableState = availableState.copy(
                            selectedCountry = "United States"
                        )
                    }
                    _detailState.update {
                        it.copy(
                            details = result,
                            watchCountry = "United States",
                            loading = false
                        )
                    }
                }
        }
    }

    private fun getCollection(collectionId: Int, language: String? = null) {
        viewModelScope.launch {
            detailRepository.getCollection(collectionId, language)
                .onError { error ->
                    println(error.name)
                }
                .onSuccess { result ->
                    _detailState.update { it.copy(collection = result) }
                }
        }
    }

    fun detailUiEvent(event: DetailUiEvent) {
        when (event) {
            DetailUiEvent.OnNavigateBack -> {}
            is DetailUiEvent.OnNavigateTo -> {}
            is DetailUiEvent.SetSelectedCountry -> {
                availableState = availableState.copy(
                    selectedCountry = event.country,
                    isSearchActive = false
                )
                _availableSearchQuery.value = ""
            }
            is DetailUiEvent.SetAvailableSearchQuery -> {
                _availableSearchQuery.value = event.query
            }
            DetailUiEvent.ChangeAvailableSearchState -> {
                availableState = availableState.copy(
                    isSearchActive = !availableState.isSearchActive
                )
            }
            DetailUiEvent.ChangeAvailableDialogState -> {
                availableState = availableState.copy(
                    isDialogShown = !availableState.isDialogShown
                )
            }
        }
    }
}
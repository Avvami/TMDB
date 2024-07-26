package com.personal.tmdb.detail.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.R
import com.personal.tmdb.core.domain.util.appendToResponse
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.core.util.convertMediaType
import com.personal.tmdb.detail.domain.models.CollectionInfo
import com.personal.tmdb.detail.domain.models.MediaDetailInfo
import com.personal.tmdb.detail.domain.models.SeasonInfo
import com.personal.tmdb.detail.domain.repository.DetailRepository
import com.personal.tmdb.detail.presentation.collection.CollectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
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

    var seasonState by mutableStateOf(SeasonState())
        private set

    var selectedSeasonNumber by mutableIntStateOf(0)
        private set

    var pageLabelsRes by mutableStateOf(listOf<Int>())
        private set

    var pagerPageCount by mutableStateOf<Int?>(null)
        private set

    var selectedTab by mutableIntStateOf(0)
        private set

    var availableState by mutableStateOf(AvailableState())
        private set

    private val _availableSearchQuery = MutableStateFlow("")
    var availableSearchQuery = _availableSearchQuery.asStateFlow()

    private val _availableCountries = MutableStateFlow(detailState.mediaDetail?.watchProviders?.keys)
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
            mediaType = savedStateHandle[C.MEDIA_TYPE] ?: "",
            mediaId = savedStateHandle[C.MEDIA_ID] ?: 0,
            appendToResponse = appendToResponse(savedStateHandle[C.MEDIA_TYPE] ?: "")
        )
    }

    private fun getMediaDetails(mediaType: String, mediaId: Int, language: String? = null, appendToResponse: String? = null) {
        viewModelScope.launch {
            detailState = detailState.copy(
                isLoading = true
            )

            var mediaDetail: MediaDetailInfo? = null
            var error: String? = null

            detailRepository.getMediaDetail(mediaType, mediaId, language, appendToResponse).let { result ->
                when (result) {
                    is Resource.Error -> {
                        error = result.message
                    }
                    is Resource.Success -> {
                        mediaDetail = result.data
                    }
                }
            }
            mediaDetail?.belongsToCollection?.let { collection ->
                getCollection(collectionId = collection.id)
            }
            mediaDetail?.let { detail ->
                val labelsRes = mutableListOf<Int>()

                if (!detail.seasons.isNullOrEmpty()) {
                    val season = detail.seasons.find { it.seasonNumber == 1 } ?: detail.seasons[0]
                    selectedSeasonNumber = season.seasonNumber
                    getSeasonDetail(detail.id, selectedSeasonNumber)
                    labelsRes.add(R.string.episodes)
                }
                if (detail.watchProviders != null) {
                    _availableCountries.value = detail.watchProviders.keys
                    availableState = availableState.copy(
                        selectedCountry = "United States"
                    )
                }
                if (detail.similar != null) {
                    labelsRes.add(R.string.similar)
                }
                if (detail.recommendations != null) {
                    labelsRes.add(R.string.recommendations)
                }

                pageLabelsRes = labelsRes
                pagerPageCount = labelsRes.size
            }

            detailState = detailState.copy(
                mediaDetail = mediaDetail,
                mediaType = convertMediaType(mediaType),
                isLoading = false,
                error = error
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

    private fun getSeasonDetail(seriesId: Int, seasonNumber: Int, language: String? = null) {
        viewModelScope.launch {
            seasonState = seasonState.copy(
                isLoading = true,
                error = null
            )

            var seasonInfo: SeasonInfo? = null
            var error: String? = null

            detailRepository.getSeasonDetail(seriesId, seasonNumber, language).let { result ->
                when (result) {
                    is Resource.Error -> {
                        error = result.message
                    }
                    is Resource.Success -> {
                        seasonInfo = result.data
                    }
                }
            }

            seasonState = seasonState.copy(
                seasonInfo = seasonInfo,
                isLoading = false,
                error = error
            )
        }
    }

    var isSeasonDropdownExpanded by mutableStateOf(false)
        private set

    var isSeasonOverviewCollapsed by mutableStateOf(true)
        private set

    var isOverviewCollapsed by mutableStateOf(true)
        private set

    fun detailUiEvent(event: DetailUiEvent) {
        when (event) {
            DetailUiEvent.ChangeCollapsedOverview -> {
                isOverviewCollapsed = !isOverviewCollapsed
            }
            DetailUiEvent.ChangeCollapsedSeasonOverview -> {
                isSeasonOverviewCollapsed = !isSeasonOverviewCollapsed
            }
            DetailUiEvent.ChangeSeasonDropdownState -> {
                isSeasonDropdownExpanded = !isSeasonDropdownExpanded
            }
            is DetailUiEvent.SetSelectedSeason -> {
                isSeasonOverviewCollapsed = true
                selectedSeasonNumber = event.seasonNumber
                getSeasonDetail(event.seriesId, selectedSeasonNumber)
            }
            is DetailUiEvent.SetSelectedTab -> {
                selectedTab = event.tabIndex
            }
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
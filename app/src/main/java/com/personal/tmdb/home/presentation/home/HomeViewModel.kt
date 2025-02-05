package com.personal.tmdb.home.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.core.domain.models.MediaInfo
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.core.util.TimeWindow
import com.personal.tmdb.detail.domain.repository.DetailRepository
import com.personal.tmdb.home.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val detailRepository: DetailRepository
): ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    init {
        _homeState.update { it.copy(loading = true) }
        getTrendingList(TimeWindow.DAY)
        getNowPlaying()
    }

    private fun getTrendingList(timeWindow: TimeWindow, language: String? = null) {
        viewModelScope.launch {
            homeRepository.getTrendingList(timeWindow, language).let { result ->
                when(result) {
                    is Resource.Error -> {
                        println(result.message)
                    }
                    is Resource.Success -> {
                        val trending = result.data
                        val randomMedia = trending?.results?.randomOrNull()
                        randomMedia?.let {
                            getRandomMediaLogos(it)
                        }
                        _homeState.update {
                            it.copy(
                                loading = false,
                                trending = trending,
                                randomMedia = randomMedia
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun getRandomMediaLogos(randomMedia: MediaInfo) {
        detailRepository.getImages(
            path = C.MEDIA_IMAGES.format(randomMedia.mediaType?.name?.lowercase().toString(), randomMedia.id),
            includeImageLanguage = "en,null"
        ).let { imagesResult ->
            _homeState.update { it.copy(randomMediaLogos = imagesResult.data?.logos) }
        }
    }

    private fun getNowPlaying(language: String? = null) {
        viewModelScope.launch {
            homeRepository.getNowPlaying(language).let { result ->
                when(result) {
                    is Resource.Error -> {
                        println(result.message)
                    }
                    is Resource.Success -> {
                        _homeState.update {
                            it.copy(
                                loading = false,
                                nowPlaying = result.data
                            )
                        }
                    }
                }
            }
        }
    }

    fun homeUiEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.OnNavigateTo -> {}
        }
    }
}
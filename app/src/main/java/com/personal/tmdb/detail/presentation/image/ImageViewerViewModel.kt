package com.personal.tmdb.detail.presentation.image

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.detail.data.models.Image
import com.personal.tmdb.detail.data.models.Images
import com.personal.tmdb.detail.domain.repository.DetailRepository
import com.personal.tmdb.detail.domain.util.ImageType
import com.personal.tmdb.detail.domain.util.convertImageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailRepository: DetailRepository
): ViewModel() {

    var imagesState by mutableStateOf(ImagesState())
        private set

    var showGridView by mutableStateOf(false)
        private set

    var hideUi by mutableStateOf(false)
        private set

    private val _initialPage: String = savedStateHandle[C.IMAGE_INDEX] ?: ""
    val initialPage: Int = _initialPage.toIntOrNull() ?: 0

    private val imageType: ImageType = convertImageType(savedStateHandle[C.IMAGE_TYPE])

    init {
        val path: String = Uri.decode(savedStateHandle[C.IMAGES_PATH] ?: "")
        getImages(path)
    }

    private fun getImages(
        path: String,
        language: String? = null,
        includeImageLanguage: String? = null
    ) {
        viewModelScope.launch {
            imagesState = imagesState.copy(
                isLoading = true
            )

            var state: Images? = null
            var error: String? = null

            detailRepository.getImages(path, language, includeImageLanguage).let { result ->
                when(result) {
                    is Resource.Error -> {
                        error = result.message
                    }
                    is Resource.Success -> {
                        state = result.data
                    }
                }
            }

            val images: List<Image?>? = when(imageType) {
                ImageType.PROFILES -> state?.profiles
                ImageType.STILLS -> state?.stills
                ImageType.BACKDROPS -> state?.backdrops
                ImageType.POSTERS -> state?.posters
                ImageType.UNKNOWN -> null
            }

            imagesState = imagesState.copy(
                state = state,
                images = images,
                isLoading = false,
                error = error
            )
        }
    }

    fun imageViewerUiEvent(event: ImageViewerUiEvent) {
        when(event) {
            ImageViewerUiEvent.ChangeShowGridView -> {
                showGridView = !showGridView
            }
            ImageViewerUiEvent.ChangeHideUi -> {
                hideUi = !hideUi
            }
        }
    }
}
package com.personal.tmdb.detail.presentation.collection

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.detail.domain.models.CollectionInfo
import com.personal.tmdb.detail.domain.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailRepository: DetailRepository
): ViewModel() {

    var collectionState by mutableStateOf(CollectionState())
        private set

    init {
        getCollection(
            collectionId = savedStateHandle[C.COLLECTION_ID] ?: 0
        )
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

}
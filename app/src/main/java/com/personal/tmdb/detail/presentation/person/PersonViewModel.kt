package com.personal.tmdb.detail.presentation.person

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.core.domain.util.appendToResponse
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.detail.domain.models.PersonInfo
import com.personal.tmdb.detail.domain.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailRepository: DetailRepository
): ViewModel() {

    var personState by mutableStateOf(PersonState())
        private set

    var isBioCollapsed by mutableStateOf(true)
        private set

    var personCreditsState by mutableStateOf(PersonCreditsState())
        private set

    val personName: String = savedStateHandle[C.PERSON_NAME] ?: ""

    val personId: Int = savedStateHandle[C.PERSON_ID] ?: 0

    init {
        getPerson(personId)
    }

    private fun getPerson(personId: Int, language: String? = null) {
        viewModelScope.launch {
            personState = personState.copy(
                isLoading = true
            )

            var personInfo: PersonInfo? = null
            var error: String? = null

            detailRepository.getPerson(personId, language, appendToResponse(MediaType.PERSON.name.lowercase())).let { result ->
                when (result) {
                    is Resource.Error -> {
                        error = result.message
                    }
                    is Resource.Success -> {
                        personInfo = result.data
                    }
                }
            }
            personCreditsState = personCreditsState.copy(
                personCredits = personInfo?.combinedCreditsInfo,
                selectedDepartment = ""
            )

            personState = personState.copy(
                personInfo = personInfo,
                isLoading = false,
                error = error
            )
        }
    }

    fun personUiEvent(event: PersonUiEvent) {
        when (event) {
            PersonUiEvent.ChangeCollapsedBioState -> {
                isBioCollapsed = !isBioCollapsed
            }
        }
    }
}
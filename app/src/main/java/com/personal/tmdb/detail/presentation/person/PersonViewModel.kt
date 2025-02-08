package com.personal.tmdb.detail.presentation.person

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.personal.tmdb.core.domain.util.UiText
import com.personal.tmdb.core.domain.util.appendToResponse
import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.detail.domain.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailRepository: DetailRepository
): ViewModel() {

    private val routeData = savedStateHandle.toRoute<Route.Person>()

    private val _personState = MutableStateFlow(
        PersonState(
            personName = routeData.personName,
            personId = routeData.personId
        )
    )
    val personState = _personState.asStateFlow()

    private val _personCreditsState = MutableStateFlow(PersonCreditsState())
    val personCreditsState = _personCreditsState.asStateFlow()

    init {
        getPerson(
            personId = routeData.personId,
            appendToResponse = appendToResponse(MediaType.PERSON.name.lowercase())
        )
    }

    private fun getPerson(
        personId: Int,
        language: String? = null,
        appendToResponse: String? = null
    ) {
        viewModelScope.launch {
            _personState.update { it.copy(loading = true) }

            detailRepository.getPerson(personId, language, appendToResponse).let { result ->
                when (result) {
                    is Resource.Error -> {
                        _personState.update {
                            it.copy(
                                loading = false,
                                errorMessage = UiText.DynamicString(result.message ?: "")
                            )
                        }
                    }
                    is Resource.Success -> {
                        val personInfo = result.data
                        _personCreditsState.update {
                            it.copy(
                                personCredits = personInfo?.combinedCreditsInfo,
                                filteredPersonCredits = personInfo?.combinedCreditsInfo?.credits
                            )
                        }
                        _personState.update {
                            it.copy(
                                loading = false,
                                personInfo = personInfo
                            )
                        }
                    }
                }
            }
        }
    }

    fun personUiEvent(event: PersonUiEvent) {
        when (event) {
            PersonUiEvent.OnNavigateBack -> {}
            is PersonUiEvent.OnNavigateTo -> {}
            is PersonUiEvent.SortPersonCredits -> {
                _personCreditsState.update { state ->
                    println("Sorting update")
                    val selectedDepartment = when (event.department) {
                        state.selectedDepartment -> ""
                        "" -> state.selectedDepartment
                        else -> event.department
                    }
                    val selectedMediaType = when (event.mediaType) {
                        state.selectedMediaType -> null
                        null -> state.selectedMediaType
                        else -> event.mediaType
                    }
                    val filteredCredits = state.personCredits?.credits?.let { credits ->
                        credits.filterKeys {
                            selectedDepartment == "" || it == selectedDepartment
                        }.mapValues { (_, yearGroup) ->
                            yearGroup?.mapValues { (_, credits) ->
                                credits.filter {
                                    selectedMediaType == null || it.mediaType == selectedMediaType
                                }
                            }
                        }
                    }
                    state.copy(
                        filteredPersonCredits = filteredCredits,
                        selectedDepartment = selectedDepartment,
                        selectedMediaType = selectedMediaType
                    )
                }
            }
        }
    }
}
package com.personal.tmdb.profile.presentation.lists.presentation.list_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.personal.tmdb.core.domain.repository.PreferencesRepository
import com.personal.tmdb.core.domain.repository.UserRepository
import com.personal.tmdb.core.domain.util.onError
import com.personal.tmdb.core.domain.util.onSuccess
import com.personal.tmdb.core.domain.util.toUiText
import com.personal.tmdb.core.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val preferencesRepository: PreferencesRepository
): ViewModel() {

    private val routeData = savedStateHandle.toRoute<Route.ListDetails>()

    private val _listDetailsState = MutableStateFlow(
        ListDetailsState(
            listId = routeData.listId,
            listName = routeData.listName
        )
    )
    val listDetailsState = _listDetailsState.asStateFlow()

    init {
        getListDetails(
            listId = routeData.listId,
            page = 1
        )
    }

    private fun getListDetails(listId: Int, page: Int) {
        viewModelScope.launch {
            _listDetailsState.update {
                it.copy(
                    loading = true,
                    errorMessage = null
                )
            }

            val language = preferencesRepository.getLanguage()
            val sessionId = userRepository.getUser()?.sessionId ?: ""

            userRepository.getListDetails(listId, sessionId, page, language)
                .onError { error ->
                    _listDetailsState.update {
                        it.copy(
                            loading = false,
                            errorMessage = error.toUiText()
                        )
                    }
                }
                .onSuccess { result ->
                    _listDetailsState.update {
                        it.copy(
                            loading = false,
                            listDetails = result
                        )
                    }
                }
        }
    }

    fun listDetailsUiEvent(event: ListDetailsUiEvent) {
        when (event) {
            ListDetailsUiEvent.OnNavigateBack -> {}
            is ListDetailsUiEvent.OnNavigateTo -> {}
        }
    }
}
package com.personal.tmdb.profile.presentation.lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.core.domain.repository.UserRepository
import com.personal.tmdb.core.domain.util.onError
import com.personal.tmdb.core.domain.util.onSuccess
import com.personal.tmdb.core.domain.util.toUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _listsState = MutableStateFlow(ListsState())
    val listsState = _listsState.asStateFlow()

    private fun getLists(page: Int) {
        viewModelScope.launch {
            _listsState.update {
                it.copy(
                    loading = true,
                    errorMessage = null
                )
            }
            val user = userRepository.getUser()

            userRepository.getLists(accountObjectId = user?.accountObjectId ?: "", sessionId = user?.sessionId ?: "", page = page)
                .onError { error ->
                    _listsState.update {
                        it.copy(
                            loading = false,
                            errorMessage = error.toUiText()
                        )
                    }
                }
                .onSuccess { result ->
                    _listsState.update {
                        it.copy(
                            loading = false,
                            lists = result
                        )
                    }
                }
        }
    }

    fun listsUiEvent(event: ListsUiEvent) {
        when (event) {
            ListsUiEvent.OnNavigateBack -> {}
            is ListsUiEvent.OnNavigateTo -> {}
            is ListsUiEvent.GetLists -> {
                getLists(event.page)
            }
        }
    }
}
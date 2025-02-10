package com.personal.tmdb

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.auth.data.models.AccessTokenBody
import com.personal.tmdb.auth.data.models.RedirectToBody
import com.personal.tmdb.auth.data.models.RequestTokenBody
import com.personal.tmdb.auth.domain.repository.AuthRepository
import com.personal.tmdb.core.domain.repository.LocalCache
import com.personal.tmdb.core.domain.repository.LocalRepository
import com.personal.tmdb.core.domain.util.C
import com.personal.tmdb.core.domain.util.SnackbarController
import com.personal.tmdb.core.domain.util.SnackbarEvent
import com.personal.tmdb.core.domain.util.UiText
import com.personal.tmdb.core.domain.util.onError
import com.personal.tmdb.core.domain.util.onSuccess
import com.personal.tmdb.core.domain.util.toUiText
import com.personal.tmdb.core.presentation.PreferencesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val authRepository: AuthRepository,
    private val localCache: LocalCache
): ViewModel() {

    var holdSplash by mutableStateOf(true)
        private set

    private val _preferencesState = MutableStateFlow(PreferencesState())
    val preferencesState: StateFlow<PreferencesState> = _preferencesState.asStateFlow()

    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    init {
        val preferencesFlow = localRepository.getPreferences().shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            replay = 1
        )

        viewModelScope.launch {
            preferencesFlow.collect { preferencesEntity ->
                _preferencesState.update {
                    it.copy(
                        darkTheme = preferencesEntity.darkTheme,
                        language = preferencesEntity.language,
                        corners = preferencesEntity.corners,
                        useCards = preferencesEntity.useCards,
                        showTitle = preferencesEntity.showTitle,
                        showVoteAverage = preferencesEntity.showVoteAverage
                    )
                }
                holdSplash = false
            }
        }
        viewModelScope.launch {
            preferencesFlow
                .distinctUntilChangedBy { listOf(it.accessToken, it.accountId, it.sessionId) }
                .collect { preferencesEntity ->
                    _userState.update {
                        it.copy(
                            accessToken = preferencesEntity.accessToken,
                            accountId = preferencesEntity.accountId,
                            sessionId = preferencesEntity.sessionId
                        )
                    }
                    getUserDetails(preferencesEntity.sessionId)
                }
        }
    }

    private fun createRequestToken() {
        viewModelScope.launch {
            var requestToken: String? = null
            _userState.update { it.copy(loading = true) }

            authRepository.createRequestToken(RedirectToBody("${C.REDIRECT_URL}/true"))
                .onError { error ->
                    _userState.update { it.copy(errorMessage = error.toUiText()) }
                }
                .onSuccess { result ->
                    if (result.success) {
                        requestToken = result.requestToken
                    } else {
                        _userState.update { it.copy(errorMessage = UiText.DynamicString(result.statusMessage)) }
                    }
                }

            _userState.update { it.copy(loading = false) }
            requestToken?.let { token ->
                _userState.update { it.copy(requestToken = token) }
                localCache.saveRequestToken(token)
            }
        }
    }

    private fun signInUser() {
        viewModelScope.launch {
            authRepository.createAccessToken(RequestTokenBody(localCache.getRequestToken()))
                .onError { error ->
                    _userState.update { it.copy(errorMessage = error.toUiText()) }
                }
                .onSuccess { accessToken ->
                    if (accessToken.success && accessToken.accessToken != null && accessToken.accountId != null) {
                        authRepository.createSession(AccessTokenBody(accessToken.accessToken))
                            .onError { error ->
                                SnackbarController.sendEvent(
                                    event = SnackbarEvent(
                                        message = error.toUiText()
                                    )
                                )
                            }
                            .onSuccess { session ->
                                if (session.success && session.sessionId != null) {
                                    localRepository.setAccessInfo(
                                        accessToken = accessToken.accessToken,
                                        sessionId = session.sessionId,
                                        accountId = accessToken.accountId
                                    )
                                    localCache.clearRequestToken()
                                    SnackbarController.sendEvent(
                                        event = SnackbarEvent(
                                            message = UiText.StringResource(R.string.signed_in_successfully)
                                        )
                                    )
                                } else {
                                    SnackbarController.sendEvent(
                                        event = SnackbarEvent(
                                            message = UiText.StringResource(R.string.error_session_id)
                                        )
                                    )
                                }
                            }
                    } else {
                        SnackbarController.sendEvent(
                            event = SnackbarEvent(
                                message = UiText.DynamicString(accessToken.statusMessage)
                            )
                        )
                    }
                }
        }
    }

    private fun getUserDetails(sessionId: String) {
        if (sessionId.isBlank()) return
        viewModelScope.launch {
            _userState.update { it.copy(loading = true) }

            authRepository.getUserDetails(sessionId)
                .onError { error ->
                    println(error.name)
                }
                .onSuccess { result ->
                    _userState.update {
                        it.copy(
                            loading = false,
                            userInfo = result
                        )
                    }
                }
        }
    }

    fun uiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.SetTheme -> {
                viewModelScope.launch {
                    localRepository.setTheme(event.darkTheme)
                }
            }
            is UiEvent.SetShowTitle -> {
                viewModelScope.launch {
                    localRepository.setShowTitle(event.showTitle)
                }
            }
            is UiEvent.SetShowVoteAverage -> {
                viewModelScope.launch {
                    localRepository.setShowVoteAverage(event.showVoteAverage)
                }
            }
            UiEvent.CreateRequestToken -> {
                createRequestToken()
            }
            UiEvent.DropRequestToken -> {
                _userState.update { it.copy(requestToken = null) }
            }
            UiEvent.SignInUser -> {
                signInUser()
            }
            UiEvent.SignOut -> {

            }
        }
    }
}
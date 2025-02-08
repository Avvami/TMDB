package com.personal.tmdb

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.tmdb.auth.data.models.AccessTokenBody
import com.personal.tmdb.auth.data.models.RedirectToBody
import com.personal.tmdb.auth.data.models.RequestTokenBody
import com.personal.tmdb.auth.domain.models.UserInfo
import com.personal.tmdb.auth.domain.repository.AuthRepository
import com.personal.tmdb.core.domain.repository.LocalCache
import com.personal.tmdb.core.domain.repository.LocalRepository
import com.personal.tmdb.core.domain.util.SnackbarController
import com.personal.tmdb.core.domain.util.SnackbarEvent
import com.personal.tmdb.core.domain.util.UiText
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.domain.util.C
import com.personal.tmdb.core.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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

    private var cornersJob: Job? = null

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
            _userState.update { it.copy(isLoading = true) }

            authRepository.createRequestToken(RedirectToBody("${C.REDIRECT_URL}/true")).let { result ->
                when(result) {
                    is Resource.Error -> {
                        _userState.update { it.copy(error = result.message) }
                    }
                    is Resource.Success -> {
                        if (result.data?.success == true) {
                            requestToken = result.data.requestToken
                        } else {
                            _userState.update { it.copy(error = result.data?.statusMessage) }
                        }
                    }
                }
            }

            _userState.update { it.copy(isLoading = false) }
            requestToken?.let { token ->
                _userState.update { it.copy(requestToken = token) }
                localCache.saveRequestToken(token)
            }
        }
    }

    private fun signInUser() {
        viewModelScope.launch {
            authRepository.createAccessToken(RequestTokenBody(localCache.getRequestToken())).let { result ->
                when(result) {
                    is Resource.Error -> {
                        _userState.update { it.copy(error = result.message) }
                    }
                    is Resource.Success -> {
                        if (result.data?.success == true && result.data.accessToken != null && result.data.accountId != null) {
                            authRepository.createSession(AccessTokenBody(result.data.accessToken)).let { sessionResult ->
                                when(sessionResult) {
                                    is Resource.Error -> {
                                        _userState.update { it.copy(error = result.message) }
                                    }
                                    is Resource.Success -> {
                                        if (sessionResult.data?.success == true && sessionResult.data.sessionId != null) {
                                            localRepository.setAccessInfo(
                                                accessToken = result.data.accessToken,
                                                sessionId = sessionResult.data.sessionId,
                                                accountId = result.data.accountId
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
                                                    message = UiText.DynamicString("Error obtaining session ID")
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        } else {
                            result.data?.statusMessage?.let {
                                SnackbarController.sendEvent(
                                    event = SnackbarEvent(
                                        message = UiText.DynamicString(it)
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getUserDetails(sessionId: String) {
        if (sessionId.isBlank()) return
        viewModelScope.launch {
            _userState.update { it.copy(isLoading = true) }

            var userInfo: UserInfo? = null

            authRepository.getUserDetails(sessionId).let { result ->
                when (result) {
                    is Resource.Error -> {
                        /*TODO: Something about it?*/
                    }
                    is Resource.Success -> {
                        userInfo = result.data
                    }
                }
            }

            _userState.update {
                it.copy(
                    isLoading = false,
                    userInfo = userInfo
                )
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
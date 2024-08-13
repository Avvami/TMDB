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
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.core.util.TimeWindow
import com.personal.tmdb.home.domain.repository.HomeRepository
import com.personal.tmdb.home.presentation.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val homeRepository: HomeRepository,
    private val authRepository: AuthRepository,
    private val localCache: LocalCache
): ViewModel() {

    var holdSplash by mutableStateOf(true)
        private set

    private val _preferencesState = MutableStateFlow(PreferencesState())
    val preferencesState: StateFlow<PreferencesState> = _preferencesState.asStateFlow()

    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    var homeState by mutableStateOf(HomeState())
        private set

    init {
        viewModelScope.launch {
            localRepository.getPreferences().collect { preferencesEntity ->
                _preferencesState.update {
                    it.copy(
                        darkTheme = preferencesEntity.darkTheme,
                        sessionId = preferencesEntity.sessionId
                    )
                }
                holdSplash = false
            }
        }
        getTrendingList(TimeWindow.DAY)
    }

    private fun getTrendingList(timeWindow: TimeWindow, language: String? = null) {
        viewModelScope.launch {
            var trending = homeState.trending

            homeRepository.getTrendingList(timeWindow, language).let { result ->
                when(result) {
                    is Resource.Error -> {
                        println(result.message)
                    }
                    is Resource.Success -> {
                        trending = result.data
                    }
                }
            }

            homeState = homeState.copy(
                trending = trending,
                randomMedia = trending?.results?.randomOrNull()
            )
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
            _userState.update { it.copy(isLoading = true) }

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
                                            _userState.update { it.copy(showSnackDone = true) }
                                        } else {
                                            _userState.update { it.copy(error = "Error obtaining session ID") }
                                        }
                                    }
                                }
                            }
                        } else {
                            _userState.update { it.copy(error = result.data?.statusMessage) }
                        }
                    }
                }
            }

            _userState.update { it.copy(isLoading = false) }
        }
    }

    fun uiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.SetTheme -> {
                viewModelScope.launch {
                    localRepository.setTheme(event.darkTheme)
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
            UiEvent.DropSnackDone -> {
                _userState.update { it.copy(showSnackDone = false) }
            }
            UiEvent.DropError -> {
                _userState.update { it.copy(error = null) }
            }
        }
    }
}
package com.personal.tmdb.settings.presentation.appearance

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.UiEvent
import com.personal.tmdb.UserState
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.MediaCardPreview
import com.personal.tmdb.core.presentation.components.MediaPosterPreview
import com.personal.tmdb.settings.presentation.appearance.components.ListView

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun AppearanceScreen(
    navigateBack: () -> Unit,
    onNavigateTo: (route: String) -> Unit,
    preferencesState: State<PreferencesState>,
    userState: State<UserState>,
    uiEvent: (UiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.appearance))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                ),
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack
                    )  {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SharedTransitionLayout {
                        AnimatedContent(
                            targetState = preferencesState.value.useCards,
                            label = "Media animation"
                        ) { targetState ->
                            if (targetState) {
                                MediaCardPreview(
                                    showVoteAverage = preferencesState.value.showVoteAverage,
                                    corners = preferencesState.value.corners,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedVisibilityScope = this@AnimatedContent
                                )
                            } else {
                                MediaPosterPreview(
                                    showTitle = preferencesState.value.showTitle,
                                    showVoteAverage = preferencesState.value.showVoteAverage,
                                    corners = preferencesState.value.corners,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedVisibilityScope = this@AnimatedContent
                                )
                            }
                        }
                    }
                }
            }
            item {
                ListView(
                    preferencesState = preferencesState,
                    uiEvent = uiEvent
                )
            }
        }
    }
}
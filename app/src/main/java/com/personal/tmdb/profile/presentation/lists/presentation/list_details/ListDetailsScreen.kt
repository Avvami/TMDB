package com.personal.tmdb.profile.presentation.lists.presentation.list_details

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.personal.tmdb.R
import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.MediaGrid
import com.personal.tmdb.core.presentation.components.MediaPoster

@Composable
fun ListDetailsScreenRoot(
    bottomPadding: Dp,
    onNavigateBack: () -> Unit,
    onNavigateTo: (route: Route) -> Unit,
    preferencesState: () -> PreferencesState,
    viewModel: ListDetailsViewModel = hiltViewModel()
) {
    val listDetailsState by viewModel.listDetailsState.collectAsStateWithLifecycle()
    ListDetailsScreen(
        modifier = Modifier.padding(bottom = bottomPadding),
        preferencesState = preferencesState,
        listDetailsState = { listDetailsState },
        listDetailsUiEvent = { event ->
            when (event) {
                ListDetailsUiEvent.OnNavigateBack -> onNavigateBack()
                is ListDetailsUiEvent.OnNavigateTo -> onNavigateTo(event.route)
                else -> Unit
            }
            viewModel.listDetailsUiEvent(event)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListDetailsScreen(
    modifier: Modifier = Modifier,
    preferencesState: () -> PreferencesState,
    listDetailsState: () -> ListDetailsState,
    listDetailsUiEvent: (ListDetailsUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.my_lists))
                },
                navigationIcon = {
                    IconButton(
                        onClick = { listDetailsUiEvent(ListDetailsUiEvent.OnNavigateBack) }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = null
                        )
                    }
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Share,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) { innerPadding ->
        if (listDetailsState().loading) {

        } else {
            listDetailsState().listDetails?.let { listDetails ->
                MediaGrid(
                    modifier = modifier.padding(top = innerPadding.calculateTopPadding()),
                    contentPadding = PaddingValues(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    listDetails.results?.let { results ->
                        items(
                            items = results,
                            key = { it.id }
                        ) { mediaInfo ->
                            MediaPoster(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateItem(),
                                onNavigateTo = { listDetailsUiEvent(ListDetailsUiEvent.OnNavigateTo(it)) },
                                height = Dp.Unspecified,
                                mediaInfo = mediaInfo,
                                mediaType = mediaInfo.mediaType,
                                showTitle = preferencesState().showTitle,
                                showVoteAverage = preferencesState().showVoteAverage
                            )
                        }
                    }
                }
            }
        }
    }
}
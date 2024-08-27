package com.personal.tmdb.detail.presentation.episodes

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
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
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.personal.tmdb.R
import com.personal.tmdb.UserState
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.CustomIconButton
import com.personal.tmdb.core.util.UiText
import com.personal.tmdb.core.util.shareText
import com.personal.tmdb.detail.presentation.episodes.components.seasonInfo
import com.personal.tmdb.detail.presentation.episodes.components.seasonInfoShimmer
import com.personal.tmdb.detail.presentation.episodes.components.seasonSelect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodesScreen(
    navigateBack: () -> Unit,
    onNavigateTo: (route: String) -> Unit,
    navigateToHome: () -> Unit,
    preferencesState: State<PreferencesState>,
    userState: State<UserState>,
    episodesViewModel: EpisodesViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.all_episodes),
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    CustomIconButton(
                        onClick = navigateBack,
                        onLongClick = navigateToHome
                    )  {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
                actions = {
                    val context = LocalContext.current
                    IconButton(
                        onClick = {
                            context.shareText(
                                UiText.StringResource(
                                    resId = R.string.share_season,
                                    episodesViewModel.mediaId,
                                    episodesViewModel.selectedSeasonNumber
                                ).asString(context)
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Share,
                            contentDescription = "Share"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            seasonSelect(
                seasonState = episodesViewModel::seasonState,
                selectedSeasonNumber = episodesViewModel::selectedSeasonNumber,
                isSeasonDialogOpen = episodesViewModel::isSeasonDialogOpen,
                episodesUiEvent = episodesViewModel::episodesUiEvent
            )
            if (episodesViewModel.seasonState.isLoading) {
                seasonInfoShimmer()
            } else {
                episodesViewModel.seasonState.seasonInfo?.let { seasonInfo ->
                    seasonInfo(
                        onNavigateTo = onNavigateTo,
                        seriesId = episodesViewModel::mediaId,
                        seasonInfo = { seasonInfo },
                        isOverviewCollapsed = episodesViewModel::isOverviewCollapsed,
                        preferencesState = preferencesState,
                        episodesUiEvent = episodesViewModel::episodesUiEvent
                    )
                }
            }
        }
    }
}
package com.personal.tmdb.detail.presentation.episodes

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.personal.tmdb.R
import com.personal.tmdb.UserState
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.detail.presentation.episodes.components.seasonInfo
import com.personal.tmdb.detail.presentation.episodes.components.seasonInfoShimmer
import com.personal.tmdb.detail.presentation.episodes.components.seasonSelect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodesScreen(
    navigateBack: () -> Unit,
    onNavigateTo: (route: String) -> Unit,
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
                    IconButton(
                        onClick = navigateBack
                    )  {
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
                            painter = painterResource(id = R.drawable.icon_translate_fill0_wght400),
                            contentDescription = "Translations"
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
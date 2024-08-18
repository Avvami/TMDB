package com.personal.tmdb.detail.presentation.episode

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.UserState
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.util.C
import com.personal.tmdb.detail.presentation.episode.components.EpisodeDetails
import com.personal.tmdb.ui.theme.backgroundLight
import com.personal.tmdb.ui.theme.onBackgroundLight

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun EpisodeDetailsScreen(
    navigateBack: () -> Unit,
    onNavigateTo: (route: String) -> Unit,
    preferencesState: State<PreferencesState>,
    userState: State<UserState>,
    episodeDetailsViewModel: EpisodeDetailsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            id = R.string.season_episode,
                            episodeDetailsViewModel.seasonNumber,
                            episodeDetailsViewModel.episodeNumber
                        ),
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
                    AnimatedVisibility(
                        visible = !episodeDetailsViewModel.episodeDetailsState.episodeDetails?.translations?.translations.isNullOrEmpty()
                    ) {
                        IconButton(
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_translate_fill0_wght400),
                                contentDescription = "Translations"
                            )
                        }
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
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            if (episodeDetailsViewModel.episodeDetailsState.isLoading) {

            } else {
                episodeDetailsViewModel.episodeDetailsState.error?.let {
                    item {
                        Box(
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceContainerHigh),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.Center),
                                text = stringResource(id = R.string.tmdb),
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.outlineVariant,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                episodeDetailsViewModel.episodeDetailsState.episodeDetails?.let { info ->
                    item {
                        Box(
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = stringResource(id = R.string.tmdb),
                                style = MaterialTheme.typography.displayMedium,
                                color = MaterialTheme.colorScheme.surfaceContainerLow,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Black,
                                textAlign = TextAlign.Center
                            )
                            info.images?.stills?.let { stills ->
                                if (stills.isNotEmpty()) {
                                    val horizontalPagerState = rememberPagerState(
                                        pageCount = { stills.size }
                                    )
                                    CompositionLocalProvider(
                                        LocalOverscrollConfiguration provides null
                                    ) {
                                        HorizontalPager(
                                            state = horizontalPagerState
                                        ) { page ->
                                            AsyncImage(
                                                modifier = Modifier.fillMaxSize(),
                                                model = C.TMDB_IMAGES_BASE_URL + C.BACKDROP_W1280 + stills[page]?.filePath,
                                                contentDescription = "Backdrop",
                                                placeholder = painterResource(id = R.drawable.placeholder),
                                                error = painterResource(id = R.drawable.placeholder),
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                    }
                                    Text(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .clip(MaterialTheme.shapes.extraSmall)
                                            .background(onBackgroundLight)
                                            .padding(horizontal = 4.dp, vertical = 2.dp)
                                            .align(Alignment.BottomEnd),
                                        text = stringResource(
                                            id = R.string.stills_pages,
                                            (horizontalPagerState.currentPage + 1),
                                            horizontalPagerState.pageCount
                                        ),
                                        style = MaterialTheme.typography.labelMedium,
                                        color = backgroundLight
                                    )
                                }
                            }
                        }
                    }
                    item {
                        EpisodeDetails(
                            info = { info },
                            isOverviewCollapsed = episodeDetailsViewModel::isOverviewCollapsed,
                            episodeDetailsUiEvent = episodeDetailsViewModel::episodeDetailsUiEvent
                        )
                    }
                }
            }
        }
    }
}
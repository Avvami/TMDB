package com.personal.tmdb.detail.presentation.episode

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.UserState
import com.personal.tmdb.core.navigation.RootNavGraph
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.MediaRowView
import com.personal.tmdb.core.util.C
import com.personal.tmdb.detail.data.models.Cast
import com.personal.tmdb.detail.presentation.episode.components.EpisodeDetails
import com.personal.tmdb.detail.presentation.episode.components.EpisodeDetailsShimmer
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
                item {
                    EpisodeDetailsShimmer()
                }
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
                                            state = horizontalPagerState,
                                            beyondViewportPageCount = 1
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
                    if (!info.guestStars.isNullOrEmpty()) {
                        item {
                            MediaRowView(
                                onNavigateTo = {
                                    onNavigateTo(RootNavGraph.CAST + "/${Uri.encode(info.name ?: "") ?: ""}/tv/${episodeDetailsViewModel.seriesId}" +
                                            "?${C.SEASON_NUMBER}=${episodeDetailsViewModel.seasonNumber}?${C.EPISODE_NUMBER}=${episodeDetailsViewModel.episodeNumber}")
                                },
                                titleRes = R.string.cast_and_crew,
                                titleFontSize = 20.sp,
                                items = {
                                    items(
                                        count = info.guestStars.size,
                                        key = { info.guestStars[it].id }
                                    ) { index ->
                                        val guestInfo = info.guestStars[index]
                                        GuestStar(
                                            onNavigateTo = onNavigateTo,
                                            guestInfo = guestInfo
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GuestStar(
    onNavigateTo: (route: String) -> Unit,
    guestInfo: Cast
) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .clip(MaterialTheme.shapes.small)
            .clickable { onNavigateTo(RootNavGraph.PERSON + "/${guestInfo.name}/${guestInfo.id}") }
        ,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            model = C.TMDB_IMAGES_BASE_URL + C.POSTER_W300 + guestInfo.profilePath,
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder),
            contentDescription = "Profile",
            contentScale = ContentScale.Crop
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = guestInfo.name,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            guestInfo.character?.let { character ->
                Text(
                    text = character,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
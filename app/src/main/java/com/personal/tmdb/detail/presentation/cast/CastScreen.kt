package com.personal.tmdb.detail.presentation.cast

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.components.CustomIconButton
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.formatEpisodesCount
import com.personal.tmdb.core.util.shareText
import com.personal.tmdb.detail.presentation.cast.components.CastInfoCard
import com.personal.tmdb.detail.presentation.cast.components.CastScreenShimmer

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CastScreen(
    navigateBack: () -> Unit,
    onNavigateTo: (route: String) -> Unit,
    navigateToHome: () -> Unit,
    castViewModel: CastViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = castViewModel.mediaName,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                ),
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
                            if (castViewModel.episodeNumber != null && castViewModel.seasonNumber != null) {
                                context.shareText(
                                    C.SHARE_EPISODE_CAST.format(
                                        castViewModel.mediaId,
                                        castViewModel.seasonNumber,
                                        castViewModel.episodeNumber
                                    )
                                )
                            } else {
                                context.shareText(
                                    C.SHARE_CAST.format(
                                        castViewModel.mediaType,
                                        castViewModel.mediaId
                                    )
                                )
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Share,
                            contentDescription = "Share"
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            if (castViewModel.castState.isLoading) {
                item {
                    CastScreenShimmer()
                }
            } else {
                castViewModel.castState.error?.let { error ->
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            text = error,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                castViewModel.castState.credits?.let { credits ->
                    if (!credits.cast.isNullOrEmpty()) {
                        stickyHeader(
                            contentType = "mainHeader"
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.surfaceContainer)
                                    .padding(horizontal = 16.dp, vertical = 4.dp),
                            ) {
                                Text(
                                    text = stringResource(id = R.string.cast),
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                        items(credits.cast) { cast ->
                            CastInfoCard(
                                onNavigateTo = onNavigateTo,
                                profileId = cast.id,
                                profilePath = cast.profilePath,
                                name = cast.name,
                                character = cast.character,
                                activity = cast.roles?.joinToString(", ") { "${it.character} (${formatEpisodesCount(it.episodeCount)})" }
                            )
                        }
                    }
                    if (!credits.guestStars.isNullOrEmpty()) {
                        stickyHeader(
                            contentType = "mainHeader"
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.surfaceContainer)
                                    .padding(horizontal = 16.dp, vertical = 4.dp),
                            ) {
                                Text(
                                    text = stringResource(id = R.string.guest_stars),
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                        items(credits.guestStars) { cast ->
                            CastInfoCard(
                                onNavigateTo = onNavigateTo,
                                profileId = cast.id,
                                profilePath = cast.profilePath,
                                name = cast.name,
                                character = cast.character,
                                activity = cast.roles?.joinToString(", ") { "${it.character} (${formatEpisodesCount(it.episodeCount)})" }
                            )
                        }
                    }
                    if (!credits.crew.isNullOrEmpty()) {
                        stickyHeader(
                            contentType = "mainHeader"
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.surfaceContainer)
                                    .padding(horizontal = 16.dp, vertical = 4.dp),
                            ) {
                                Text(
                                    text = stringResource(id = R.string.crew),
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                        credits.crew.forEach { (department, crewList) ->
                            if (!department.isNullOrEmpty()) {
                                stickyHeader(
                                    contentType = "subHeader"
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(MaterialTheme.colorScheme.surfaceContainerLow)
                                            .padding(horizontal = 16.dp, vertical = 4.dp),
                                    ) {
                                        Text(
                                            text = department,
                                            style = MaterialTheme.typography.titleSmall
                                        )
                                    }
                                }
                            }
                            if (!crewList.isNullOrEmpty()) {
                                items(crewList) { crew ->
                                    CastInfoCard(
                                        onNavigateTo = onNavigateTo,
                                        profileId = crew.id,
                                        profilePath = crew.profilePath,
                                        name = crew.name,
                                        department = department,
                                        activity = crew.jobs?.joinToString(", ") { "${it.job} (${formatEpisodesCount(it.episodeCount)})" }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
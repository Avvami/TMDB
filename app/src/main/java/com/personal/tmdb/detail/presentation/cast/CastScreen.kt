package com.personal.tmdb.detail.presentation.cast

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.formatEpisodesCount

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CastScreen(
    navigateBack: () -> Unit,
    onNavigateTo: (route: String) -> Unit,
    castViewModel: CastViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = castViewModel.mediaName,
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
            modifier = Modifier.padding(innerPadding)
        ) {
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
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { }
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(CircleShape),
                                model = C.TMDB_IMAGES_BASE_URL + C.PROFILE_W185 + cast.profilePath,
                                placeholder = painterResource(id = R.drawable.placeholder),
                                error = painterResource(id = R.drawable.placeholder),
                                contentDescription = "Profile",
                                contentScale = ContentScale.Crop
                            )
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = cast.name,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                cast.character?.let { character ->
                                    Text(
                                        text = character,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                if (!cast.roles.isNullOrEmpty()) {
                                    Text(
                                        text = cast.roles.joinToString(", ") { "${it.character} (${formatEpisodesCount(it.episodeCount)})" },
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
                credits.crew?.let { crew ->
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
                    crew.forEach { (department, crewList) ->
                        department?.let {
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
                        crewList?.let { list ->
                            items(list) { crew ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { }
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    AsyncImage(
                                        modifier = Modifier
                                            .size(52.dp)
                                            .clip(CircleShape),
                                        model = C.TMDB_IMAGES_BASE_URL + C.PROFILE_W185 + crew.profilePath,
                                        placeholder = painterResource(id = R.drawable.placeholder),
                                        error = painterResource(id = R.drawable.placeholder),
                                        contentDescription = "Profile",
                                        contentScale = ContentScale.Crop
                                    )
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = crew.name,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        crew.job?.let { job ->
                                            Text(
                                                text = job,
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                        if (!crew.jobs.isNullOrEmpty()) {
                                            Text(
                                                text = crew.jobs.joinToString(", ") { "${it.job} (${formatEpisodesCount(it.episodeCount)})" },
                                                style = MaterialTheme.typography.bodySmall
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
    }
}
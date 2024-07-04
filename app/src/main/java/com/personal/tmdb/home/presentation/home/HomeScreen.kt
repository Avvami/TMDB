package com.personal.tmdb.home.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.UiEvent
import com.personal.tmdb.core.navigation.RootNavGraph
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.GradientButton
import com.personal.tmdb.core.presentation.components.MediaPoster
import com.personal.tmdb.core.presentation.components.MediaPosterShimmer
import com.personal.tmdb.core.util.ApplySystemBarsTheme
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.duotoneColorFilter
import com.personal.tmdb.core.util.shimmerEffect
import com.personal.tmdb.home.presentation.home.components.drawer.HomeModalDrawer
import com.personal.tmdb.ui.theme.backgroundLight
import com.personal.tmdb.ui.theme.duotoneBlueDark
import com.personal.tmdb.ui.theme.duotoneBlueLight
import com.personal.tmdb.ui.theme.duotonePurpleDark
import com.personal.tmdb.ui.theme.duotonePurpleLight
import com.personal.tmdb.ui.theme.gradientPurpleDark
import com.personal.tmdb.ui.theme.gradientPurpleLight
import com.personal.tmdb.ui.theme.onSecondaryContainerLight
import com.personal.tmdb.ui.theme.secondaryContainerLight
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateTo: (route: String) -> Unit,
    preferencesState: State<PreferencesState>,
    homeState: () -> HomeState,
    uiEvent: (UiEvent) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ApplySystemBarsTheme(applyLightStatusBars = preferencesState.value.isDark)
    ModalNavigationDrawer(
        drawerContent = {
            HomeModalDrawer(
                onNavigateTo = onNavigateTo,
                drawerState = drawerState,
                closeDrawer = { scope.launch { drawerState.close() } },
                preferencesState = preferencesState,
                uiEvent = uiEvent
            )
        },
        drawerState = drawerState,
        gesturesEnabled = true
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Image(
                            modifier = Modifier.height(16.dp),
                            painter = painterResource(id = R.drawable.tmdb_logo_short),
                            contentDescription = stringResource(id = R.string.app_name)
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                        actionIconContentColor = MaterialTheme.colorScheme.onBackground,
                    ),
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } }
                        )  {
                            Icon(
                                imageVector = Icons.Rounded.Menu,
                                contentDescription = "Drawer"
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { onNavigateTo(RootNavGraph.SEARCH + "/${MediaType.MULTI.name.lowercase()}") }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = "Search"
                            )
                        }
                    }
                )
            },
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SuggestionChip(
                            modifier = Modifier.height(SuggestionChipDefaults.Height),
                            onClick = { /*TODO*/ },
                            label = {
                                Text(text = stringResource(id = R.string.tv_shows))
                            }
                        )
                        SuggestionChip(
                            modifier = Modifier.height(SuggestionChipDefaults.Height),
                            onClick = { /*TODO*/ },
                            label = {
                                Text(text = stringResource(id = R.string.movies))
                            }
                        )
                        SuggestionChip(
                            modifier = Modifier.height(SuggestionChipDefaults.Height),
                            onClick = { /*TODO*/ },
                            label = {
                                Text(text = stringResource(id = R.string.people))
                            }
                        )
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .height(450.dp)
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                            .then(
                                if (!homeState().randomMedia?.backdropPath.isNullOrEmpty()) {
                                    Modifier.shadow(
                                        elevation = 8.dp,
                                        shape = RoundedCornerShape(24.dp),
                                        ambientColor = duotoneBlueLight,
                                        spotColor = duotoneBlueLight
                                    )
                                } else {
                                    Modifier.shadow(
                                        elevation = 8.dp,
                                        shape = RoundedCornerShape(24.dp)
                                    )
                                }
                            )
                            .clip(RoundedCornerShape(24.dp))
                            .background(MaterialTheme.colorScheme.outlineVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            model = C.TMDB_IMAGES_BASE_URL + C.BACKDROP_W1280 + homeState().randomMedia?.backdropPath,
                            contentDescription = "Backdrop",
                            contentScale = ContentScale.Crop,
                            colorFilter = duotoneColorFilter(duotoneBlueLight, duotoneBlueDark)
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterVertically),
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.welcome),
                                    style = MaterialTheme.typography.displayMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = backgroundLight
                                )
                                Text(
                                    text = stringResource(id = R.string.explore_now),
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Medium,
                                    color = backgroundLight
                                )
                            }
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                value = homeViewModel.searchQuery,
                                onValueChange = {
                                    homeViewModel.homeUiEvent(HomeUiEvent.OnSearchQueryChange(it))
                                },
                                placeholder = {
                                    Text(
                                        text = stringResource(id = R.string.search_placeholder),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                trailingIcon = {
                                    FilledIconButton(
                                        onClick = {
                                            onNavigateTo(
                                                RootNavGraph.SEARCH +
                                                        "/${MediaType.MULTI.name.lowercase()}?${C.SEARCH_QUERY}=${homeViewModel.searchQuery}"
                                            )
                                        },
                                        colors = IconButtonDefaults.iconButtonColors(
                                            containerColor = secondaryContainerLight,
                                            contentColor = onSecondaryContainerLight
                                        )
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                                            contentDescription = "Search"
                                        )
                                    }
                                },
                                singleLine = true,
                                shape = MaterialTheme.shapes.medium,
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedContainerColor = backgroundLight.copy(alpha = .4f),
                                    focusedTextColor = backgroundLight,
                                    unfocusedTextColor = backgroundLight,
                                    focusedPlaceholderColor = backgroundLight,
                                    unfocusedPlaceholderColor = backgroundLight,
                                    focusedBorderColor = backgroundLight,
                                    unfocusedBorderColor = Color.Transparent,
                                    cursorColor = backgroundLight
                                )
                            )
                        }
                    }
                }
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = stringResource(id = R.string.trending),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium
                        )
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (homeState().trending == null) {
                                items(15) {
                                    MediaPosterShimmer(
                                        modifier = Modifier
                                            .height(150.dp)
                                            .aspectRatio(0.675f)
                                            .clip(RoundedCornerShape(18.dp))
                                            .shimmerEffect(),
                                        showTitle = true
                                    )
                                }
                            } else {
                                homeState().trending?.results?.let { trending ->
                                    items(
                                        count = trending.size,
                                        key = { trending[it].id }
                                    ) { index ->
                                        val mediaInfo = trending[index]
                                        MediaPoster(
                                            modifier = Modifier
                                                .height(150.dp)
                                                .aspectRatio(0.675f)
                                                .clip(RoundedCornerShape(18.dp)),
                                            onNavigateTo = onNavigateTo,
                                            mediaInfo = mediaInfo,
                                            showTitle = true,
                                            showVoteAverage = true
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .paint(
                                painter = painterResource(id = R.drawable.movie_poster),
                                sizeToIntrinsics = false,
                                contentScale = ContentScale.Crop,
                                colorFilter = duotoneColorFilter(duotonePurpleLight, duotonePurpleDark)
                            )
                            .padding(horizontal = 16.dp, vertical = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.join_today),
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Medium,
                                color = backgroundLight
                            )
                            Text(
                                text = stringResource(id = R.string.join_today_benefits),
                                style = MaterialTheme.typography.bodyLarge,
                                color = backgroundLight
                            )
                        }
                        GradientButton(
                            modifier = Modifier
                                .widthIn(max = 400.dp)
                                .fillMaxWidth(),
                            onClick = { /*TODO: Open sign up link*/ },
                            gradient = Brush.horizontalGradient(listOf(gradientPurpleDark, gradientPurpleLight))
                        ) {
                            Text(text = stringResource(id = R.string.sign_up), color = backgroundLight)
                        }
                    }
                }
            }
        }
    }
}
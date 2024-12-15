package com.personal.tmdb.detail.presentation.collection

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.core.domain.models.DropdownItem
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.CustomDropdownMenu
import com.personal.tmdb.core.presentation.components.CustomIconButton
import com.personal.tmdb.core.presentation.components.MediaListView
import com.personal.tmdb.core.util.ApplyStatusBarsTheme
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.SortType
import com.personal.tmdb.core.util.applyStatusBarsTheme
import com.personal.tmdb.core.util.convertSortType
import com.personal.tmdb.core.util.formatVoteAverage
import com.personal.tmdb.core.util.shareText
import com.personal.tmdb.core.util.shimmerEffect
import com.personal.tmdb.detail.presentation.collection.components.CollectionScreenShimmer
import com.personal.tmdb.ui.theme.backgroundLight
import com.personal.tmdb.ui.theme.onBackgroundLight
import com.personal.tmdb.ui.theme.tmdbDarkBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionScreen(
    navigateBack: () -> Unit,
    onNavigateTo: (route: String) -> Unit,
    navigateToHome: () -> Unit,
    preferencesState: State<PreferencesState>,
    collectionViewModel: CollectionViewModel = hiltViewModel()
) {
    if (collectionViewModel.collectionState.collectionInfo != null) {
        ApplyStatusBarsTheme(applyLightStatusBars = true)
    }
    val view = LocalView.current
    val context = LocalContext.current
    val darkTheme = isSystemInDarkTheme()
    DisposableEffect(key1 = Unit) {
        onDispose {
            applyStatusBarsTheme(view, context, preferencesState.value.darkTheme ?: darkTheme)
        }
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    if (collectionViewModel.collectionState.isLoading) {
                        Text(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.small)
                                .shimmerEffect(),
                            text = "Collection title",
                            fontWeight = FontWeight.Medium,
                            color = Color.Transparent
                        )
                    } else {
                        collectionViewModel.collectionState.collectionInfo?.name?.let { name ->
                            Text(
                                text = name,
                                fontWeight = FontWeight.Medium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = if (collectionViewModel.collectionState.collectionInfo != null) onBackgroundLight.copy(alpha = .9f) else MaterialTheme.colorScheme.surfaceContainerLow,
                    titleContentColor = backgroundLight,
                    navigationIconContentColor = if (collectionViewModel.collectionState.collectionInfo != null) backgroundLight else MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = if (collectionViewModel.collectionState.collectionInfo != null) backgroundLight else MaterialTheme.colorScheme.onBackground
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
                    IconButton(
                        onClick = {
                            context.shareText(C.SHARE_COLLECTION.format(collectionViewModel.collectionId))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Share,
                            contentDescription = "Share"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) { innerPadding ->
        if (collectionViewModel.collectionState.isLoading) {
            CollectionScreenShimmer(
                innerPadding = innerPadding,
                preferencesState = preferencesState
            )
        } else {
            collectionViewModel.collectionState.collectionInfo?.let { collectionInfo ->
                collectionInfo.parts?.let { parts ->
                    MediaListView(
                        modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        onNavigateTo = onNavigateTo,
                        mediaList = { parts },
                        isLoading = { collectionViewModel.collectionState.isLoading },
                        preferencesState = preferencesState,
                        emptyListContent = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = R.string.nothing_to_show),
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                        },
                        topItemContent = {
                            val sidePadding = (-16).dp
                            Column(
                                modifier = Modifier
                                    .layout { measurable, constraints ->
                                        val placeable =
                                            measurable.measure(constraints.offset(horizontal = -sidePadding.roundToPx() * 2))
                                        layout(
                                            width = placeable.width + sidePadding.roundToPx() * 2,
                                            height = placeable.height
                                        ) {
                                            placeable.place(+sidePadding.roundToPx(), 0)
                                        }
                                    }
                                    .padding(bottom = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                collectionViewModel.collectionState.collectionInfo?.let { collectionInfo ->
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(IntrinsicSize.Min)
                                    ) {
                                        AsyncImage(
                                            modifier = Modifier.fillMaxSize(),
                                            model = C.TMDB_IMAGES_BASE_URL + C.BACKDROP_W1280 + collectionInfo.backdropPath,
                                            contentDescription = "Backdrop",
                                            contentScale = ContentScale.Crop,
                                            placeholder = painterResource(id = R.drawable.placeholder),
                                            error = painterResource(id = R.drawable.placeholder)
                                        )
                                        Column(
                                            modifier = Modifier
                                                .background(tmdbDarkBlue.copy(alpha = .7f))
                                                .padding(bottom = 16.dp)
                                                .padding(top = innerPadding.calculateTopPadding()),
                                            verticalArrangement = Arrangement.spacedBy(16.dp)
                                        ) {
                                            collectionInfo.overview?.let { overview ->
                                                Text(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .clip(MaterialTheme.shapes.extraSmall)
                                                        .animateContentSize()
                                                        .padding(horizontal = 16.dp)
                                                        .clickable {
                                                            collectionViewModel.collectionUiEvent(
                                                                CollectionUiEvent.ChangeCollapsedOverview
                                                            )
                                                        },
                                                    text = overview,
                                                    style = MaterialTheme.typography.bodyLarge,
                                                    color = backgroundLight,
                                                    maxLines = if (collectionViewModel.isOverviewCollapsed) 4 else Int.MAX_VALUE,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                            collectionInfo.parts?.let { mediaList ->
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceEvenly
                                                ) {
                                                    Column(
                                                        modifier = Modifier.padding(horizontal = 4.dp),
                                                        verticalArrangement = Arrangement.spacedBy(4.dp),
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
                                                        Text(
                                                            text = mediaList.size.toString(),
                                                            style = MaterialTheme.typography.titleLarge,
                                                            fontWeight = FontWeight.Medium,
                                                            color = backgroundLight
                                                        )
                                                        Text(
                                                            text = stringResource(id = R.string.number_of_movies),
                                                            style = MaterialTheme.typography.titleMedium,
                                                            color = backgroundLight
                                                        )
                                                    }
                                                    Column(
                                                        modifier = Modifier.padding(horizontal = 4.dp),
                                                        verticalArrangement = Arrangement.spacedBy(4.dp),
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
                                                        Row(
                                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Text(
                                                                text = formatVoteAverage(collectionInfo.averageRating),
                                                                style = MaterialTheme.typography.titleLarge,
                                                                fontWeight = FontWeight.Medium,
                                                                color = backgroundLight
                                                            )
                                                            Icon(
                                                                painter = painterResource(id = R.drawable.icon_bar_chart_fill0_wght400),
                                                                contentDescription = "Rating",
                                                                tint = backgroundLight
                                                            )
                                                        }
                                                        Text(
                                                            text = stringResource(id = R.string.average_rating),
                                                            style = MaterialTheme.typography.titleMedium,
                                                            color = backgroundLight
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .horizontalScroll(rememberScrollState())
                                        .padding(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    SuggestionChip(
                                        modifier = Modifier.height(SuggestionChipDefaults.Height),
                                        onClick = {
                                            collectionViewModel.collectionUiEvent(CollectionUiEvent.ChangeDropdownState)
                                        },
                                        label = {
                                            Text(text = stringResource(id = R.string.sort_by))
                                        },
                                        icon = {
                                            val rotation by animateIntAsState(
                                                targetValue = if (collectionViewModel.isDropdownExpanded) 180 else 0,
                                                label = "Rotation animation"
                                            )
                                            Icon(
                                                modifier = Modifier
                                                    .rotate(rotation.toFloat())
                                                    .size(SuggestionChipDefaults.IconSize),
                                                imageVector = Icons.Rounded.ArrowDropDown,
                                                contentDescription = "Dropdown"
                                            )
                                            CustomDropdownMenu(
                                                expanded = collectionViewModel.isDropdownExpanded,
                                                onDismissRequest = {
                                                    collectionViewModel.collectionUiEvent(
                                                        CollectionUiEvent.ChangeDropdownState
                                                    )
                                                },
                                                dropDownItems = listOf(
                                                    DropdownItem(
                                                        selected = true,
                                                        iconRes = R.drawable.icon_thumbs_up_down_fill0_wght400,
                                                        textRes = R.string.rating,
                                                        onItemClick = {
                                                            when (collectionViewModel.sortType) {
                                                                SortType.RATING_ASC -> {
                                                                    collectionViewModel.collectionUiEvent(CollectionUiEvent.SetSortType(SortType.RATING_DESC))
                                                                }
                                                                SortType.RATING_DESC -> {
                                                                    collectionViewModel.collectionUiEvent(CollectionUiEvent.SetSortType(SortType.RATING_ASC))
                                                                }
                                                                else -> {
                                                                    collectionViewModel.collectionUiEvent(CollectionUiEvent.SetSortType(SortType.RATING_DESC))
                                                                }
                                                            }
                                                            collectionViewModel.collectionUiEvent(
                                                                CollectionUiEvent.ChangeDropdownState
                                                            )
                                                        }
                                                    ),
                                                    DropdownItem(
                                                        selected = true,
                                                        iconRes = R.drawable.icon_calendar_month_fill0_wght400,
                                                        textRes = R.string.release_date,
                                                        onItemClick = {
                                                            when (collectionViewModel.sortType) {
                                                                SortType.RELEASE_DATE_ASC -> {
                                                                    collectionViewModel.collectionUiEvent(CollectionUiEvent.SetSortType(SortType.RELEASE_DATE_DESC))
                                                                }
                                                                SortType.RELEASE_DATE_DESC -> {
                                                                    collectionViewModel.collectionUiEvent(CollectionUiEvent.SetSortType(SortType.RELEASE_DATE_ASC))
                                                                }
                                                                else -> {
                                                                    collectionViewModel.collectionUiEvent(CollectionUiEvent.SetSortType(SortType.RELEASE_DATE_DESC))
                                                                }
                                                            }
                                                            collectionViewModel.collectionUiEvent(
                                                                CollectionUiEvent.ChangeDropdownState
                                                            )
                                                        }
                                                    ),
                                                )
                                            )
                                        }
                                    )
                                    AnimatedVisibility(
                                        visible = collectionViewModel.sortType != null
                                    ) {
                                        FilterChip(
                                            modifier = Modifier.height(FilterChipDefaults.Height),
                                            selected = true,
                                            onClick = {
                                                collectionViewModel.collectionUiEvent(CollectionUiEvent.SetSortType(null))
                                            },
                                            label = {
                                                Text(
                                                    text = stringResource(
                                                        id = when (collectionViewModel.sortType) {
                                                            SortType.RATING_ASC -> convertSortType(collectionViewModel.sortType)
                                                            SortType.RATING_DESC -> convertSortType(collectionViewModel.sortType)
                                                            SortType.RELEASE_DATE_ASC -> convertSortType(collectionViewModel.sortType)
                                                            SortType.RELEASE_DATE_DESC -> convertSortType(collectionViewModel.sortType)
                                                            null -> convertSortType(collectionViewModel.sortType)
                                                        }
                                                    )
                                                )
                                            },
                                            leadingIcon = {
                                                Icon(
                                                    modifier = Modifier.size(FilterChipDefaults.IconSize),
                                                    imageVector = Icons.Rounded.Clear,
                                                    contentDescription = "Clear"
                                                )
                                            },
                                            trailingIcon = {
                                                val rotation by animateIntAsState(
                                                    targetValue = when (collectionViewModel.sortType) {
                                                        SortType.RATING_ASC -> 90
                                                        SortType.RATING_DESC -> -90
                                                        SortType.RELEASE_DATE_ASC -> 90
                                                        SortType.RELEASE_DATE_DESC -> -90
                                                        null -> -90
                                                    },
                                                    label = "Rotation animation"
                                                )
                                                Icon(
                                                    modifier = Modifier
                                                        .rotate(rotation.toFloat())
                                                        .size(FilterChipDefaults.IconSize),
                                                    imageVector = when (collectionViewModel.sortType) {
                                                        SortType.RATING_ASC, SortType.RATING_DESC,
                                                        SortType.RELEASE_DATE_ASC, SortType.RELEASE_DATE_DESC -> Icons.AutoMirrored.Rounded.ArrowBack
                                                        null -> Icons.AutoMirrored.Rounded.ArrowBack
                                                    },
                                                    contentDescription = "Arrow"
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
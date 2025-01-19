package com.personal.tmdb.detail.presentation.person

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.CustomIconButton
import com.personal.tmdb.core.presentation.components.MediaPoster
import com.personal.tmdb.core.presentation.components.MediaRowView
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.shareText
import com.personal.tmdb.detail.presentation.person.components.Bio
import com.personal.tmdb.detail.presentation.person.components.ExternalIdsRow
import com.personal.tmdb.detail.presentation.person.components.PersonKnownForMedia
import com.personal.tmdb.detail.presentation.person.components.PersonPhotoCarousel
import com.personal.tmdb.detail.presentation.person.components.PersonScreenShimmer
import com.personal.tmdb.detail.presentation.person.components.PersonalInfo
import com.personal.tmdb.detail.presentation.person.components.SortModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PersonScreen(
    navigateBack: () -> Unit,
    onNavigateTo: (route: String) -> Unit,
    navigateToHome: () -> Unit,
    preferencesState: State<PreferencesState>,
    personViewModel: PersonViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    SelectionContainer {
                        Text(
                            text = personViewModel.personName,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
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
                            context.shareText(C.SHARE_PERSON.format(personViewModel.personId))
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
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 8.dp)
        ) {
            if (personViewModel.personState.isLoading) {
                item {
                    PersonScreenShimmer(
                        preferencesState = preferencesState
                    )
                }
            } else {
                personViewModel.personState.error?.let { error ->
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
                personViewModel.personState.personInfo?.let { personInfo ->
                    item {
                        PersonPhotoCarousel(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            onNavigateTo = onNavigateTo,
                            personInfo = { personInfo },
                            preferencesState = preferencesState
                        )
                    }
                    personInfo.externalIds?.let { externalIds ->
                        item {
                            ExternalIdsRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState())
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                externalIds = externalIds
                            )
                        }
                    }
                    item {
                        PersonalInfo(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            personInfo = { personInfo }
                        )
                    }
                    item {
                        Bio(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            personInfo = { personInfo },
                            isBioCollapsed = personViewModel::isBioCollapsed,
                            personUiEvent = personViewModel::personUiEvent
                        )
                    }
                    personInfo.combinedCreditsInfo?.let { combinedCredits ->
                        item {
                            MediaRowView(
                                modifier = Modifier.padding(vertical = 8.dp),
                                titleRes = if ((combinedCredits.castMediaInfo?.size ?: 0) > 2) R.string.person_starring else R.string.known_for,
                                items = {
                                    if ((combinedCredits.castMediaInfo?.size ?: 0) > 2) {
                                        combinedCredits.castMediaInfo?.let { castMediaInfo ->
                                            items(
                                                items = castMediaInfo,
                                                key = { it.id }
                                            ) { mediaInfo ->
                                                MediaPoster(
                                                    onNavigateTo = onNavigateTo,
                                                    mediaInfo = mediaInfo,
                                                    mediaType = mediaInfo.mediaType,
                                                    showTitle = preferencesState.value.showTitle,
                                                    showVoteAverage = preferencesState.value.showVoteAverage,
                                                )
                                            }
                                        }
                                    } else {
                                        combinedCredits.crewMediaInfo?.let { crewMediaInfo ->
                                            items(
                                                items = crewMediaInfo,
                                                key = { it.id }
                                            ) { mediaInfo ->
                                                MediaPoster(
                                                    onNavigateTo = onNavigateTo,
                                                    mediaInfo = mediaInfo,
                                                    mediaType = mediaInfo.mediaType,
                                                    showTitle = preferencesState.value.showTitle,
                                                    showVoteAverage = preferencesState.value.showVoteAverage,
                                                )
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                    personViewModel.personCreditsState.personCredits?.credits?.let {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 4.dp, bottom = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(id = R.string.credits),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 20.sp
                                )
                                IconButton(
                                    onClick = { personViewModel.personUiEvent(PersonUiEvent.ChangeBottomSheetState) }
                                ) {
                                    Icon(painter = painterResource(id = R.drawable.icon_page_info_fill0_wght400), contentDescription = null)
                                }
                            }
                        }
                    }
                    personViewModel.personCreditsState.filteredPersonCredits?.let { personCredits ->
                        personCredits.forEach { (department, knownFor) ->
                            if (knownFor?.values?.any { it.isNotEmpty() } == true) {
                                stickyHeader {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(MaterialTheme.colorScheme.surfaceContainer)
                                            .padding(horizontal = 16.dp, vertical = 4.dp),
                                        text = department ?: stringResource(id = R.string.acting),
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                            knownFor?.values?.forEachIndexed { groupIndex, infos ->
                                itemsIndexed(
                                    items = infos,
                                    key = { _, item -> item.uniqueId }
                                ) { index, info ->
                                    Column(
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp)
                                            .animateItem()
                                    ) {
                                        PersonKnownForMedia(
                                            modifier = Modifier.animateItem(),
                                            onNavigateTo = onNavigateTo,
                                            info = { info },
                                            preferencesState = preferencesState
                                        )
                                        if (index == infos.lastIndex && knownFor.values.size - 1 != groupIndex) {
                                            HorizontalDivider()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (personViewModel.personCreditsState.showBottomSheet) {
            SortModalBottomSheet(
                personCreditsState = personViewModel::personCreditsState,
                personUiEvent = personViewModel::personUiEvent
            )
        }
    }
}
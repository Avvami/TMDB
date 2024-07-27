package com.personal.tmdb.detail.presentation.person

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.core.navigation.RootNavGraph
import com.personal.tmdb.core.presentation.components.MediaPoster
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.formatDate
import com.personal.tmdb.core.util.formatGender
import com.personal.tmdb.core.util.formatPersonActing
import com.personal.tmdb.detail.presentation.person.components.ExternalIdsRow
import java.time.LocalDate
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PersonScreen(
    navigateBack: () -> Unit,
    onNavigateTo: (route: String) -> Unit,
    personViewModel: PersonViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = personViewModel.personName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
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
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            personViewModel.personState.personInfo?.let { personInfo ->
                item {
                    val pageCount = if ((personInfo.images?.profiles?.size ?: 0) > 1) 250 else 1
                    val horizontalPagerState = rememberPagerState(
                        pageCount = { pageCount },
                        initialPage = pageCount / 2
                    )
                    BoxWithConstraints(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val contentPadding = (maxWidth - 150.dp) / 2
                        CompositionLocalProvider(
                            LocalOverscrollConfiguration provides null
                        ) {
                            HorizontalPager(
                                state = horizontalPagerState,
                                contentPadding = PaddingValues(horizontal = contentPadding),
                                pageSpacing = (maxWidth / 150) - 8.dp,
                                flingBehavior = PagerDefaults.flingBehavior(
                                    state = horizontalPagerState,
                                    pagerSnapDistance = PagerSnapDistance.atMost(3)
                                )
                            ) { page ->
                                if (personInfo.images?.profiles.isNullOrEmpty()) {
                                    AsyncImage(
                                        modifier = Modifier
                                            .height(230.dp)
                                            .aspectRatio(0.675f)
                                            .clip(RoundedCornerShape(18.dp)),
                                        model = C.TMDB_IMAGES_BASE_URL + C.POSTER_W300,
                                        placeholder = painterResource(id = R.drawable.placeholder),
                                        error = painterResource(id = R.drawable.placeholder),
                                        contentDescription = "Profile",
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    personInfo.images?.profiles?.let { profiles ->
                                        AsyncImage(
                                            modifier = Modifier
                                                .height(230.dp)
                                                .aspectRatio(0.675f)
                                                .graphicsLayer {
                                                    val pageOffset = (
                                                            (horizontalPagerState.currentPage - page) + horizontalPagerState
                                                                .currentPageOffsetFraction
                                                            ).absoluteValue

                                                    lerp(
                                                        start = .85f,
                                                        stop = 1f,
                                                        fraction = 1f - pageOffset.absoluteValue.coerceIn(
                                                            0f,
                                                            1f
                                                        ),
                                                    ).also { scale ->
                                                        scaleX = scale
                                                        scaleY = scale
                                                    }
                                                }
                                                .clip(RoundedCornerShape(18.dp))
                                                .clickable { },
                                            model = C.TMDB_IMAGES_BASE_URL + C.POSTER_W300 + profiles[page % profiles.size]?.filePath,
                                            placeholder = painterResource(id = R.drawable.placeholder),
                                            error = painterResource(id = R.drawable.placeholder),
                                            contentDescription = "Profile",
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                personInfo.externalIds?.let { externalIds ->
                    item {
                        ExternalIdsRow(externalIds = externalIds)
                    }
                }
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.personal_info),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium
                        )
                        personInfo.knownForDepartment?.let { knownFor ->
                            Column {
                                Text(
                                    text = stringResource(id = R.string.known_for),
                                    style = MaterialTheme.typography.labelLarge
                                )
                                Text(
                                    text = knownFor,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                        Column {
                            Text(
                                text = stringResource(id = R.string.gender),
                                style = MaterialTheme.typography.labelLarge
                            )
                            Text(
                                text = formatGender(personInfo.gender, LocalContext.current),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        personInfo.birthday?.let { birthday ->
                            Column {
                                Text(
                                    text = stringResource(id = R.string.birthday),
                                    style = MaterialTheme.typography.labelLarge
                                )
                                Text(
                                    text = if (personInfo.deathday == null)
                                        "${formatDate(birthday)} (${LocalDate.now().year - birthday.year} years old)"
                                    else
                                        formatDate(birthday),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            personInfo.deathday?.let { deathday ->
                                Column {
                                    Text(
                                        text = stringResource(id = R.string.deathday),
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                    Text(
                                        text = "${formatDate(deathday)} (${deathday.year - birthday.year} years old)",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                        personInfo.placeOfBirth?.let { placeOfBirth ->
                            Column {
                                Text(
                                    text = stringResource(id = R.string.place_of_birth),
                                    style = MaterialTheme.typography.labelLarge
                                )
                                Text(
                                    text = placeOfBirth,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.bio),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium
                        )
                        if (personInfo.biography.isNullOrEmpty()) {
                            Text(
                                text = stringResource(id = R.string.no_bio, personInfo.name),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        } else {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(MaterialTheme.shapes.extraSmall)
                                    .animateContentSize()
                                    .clickable {
                                        personViewModel.personUiEvent(PersonUiEvent.ChangeCollapsedBioState)
                                    },
                                text = personInfo.biography,
                                style = MaterialTheme.typography.bodyLarge,
                                maxLines = if (personViewModel.isBioCollapsed) 6 else Int.MAX_VALUE,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
                personInfo.combinedCreditsInfo?.let { combinedCredits ->
                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = if ((combinedCredits.castMediaInfo?.size ?: 0) > 2) stringResource(id = R.string.person_starring) else stringResource(id = R.string.known_for),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Medium
                            )
                            CompositionLocalProvider(
                                LocalOverscrollConfiguration provides null
                            ) {
                                LazyRow(
                                    contentPadding = PaddingValues(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    if ((combinedCredits.castMediaInfo?.size ?: 0) > 2) {
                                        combinedCredits.castMediaInfo?.let { castMediaInfo ->
                                            items(
                                                count = castMediaInfo.size,
                                                key = { castMediaInfo[it].id }
                                            ) { index ->
                                                val mediaInfo = castMediaInfo[index]
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
                                    } else {
                                        combinedCredits.crewMediaInfo?.let { crewMediaInfo ->
                                            items(
                                                count = crewMediaInfo.size,
                                                key = { crewMediaInfo[it].id }
                                            ) { index ->
                                                val mediaInfo = crewMediaInfo[index]
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
                    }
                }
                personViewModel.personCreditsState.personCredits?.let { personCredits ->
                    item {
                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.small)
                                    .clickable { },
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_page_info_fill0_wght400),
                                    contentDescription = "Customize"
                                )
                                Text(
                                    text = if (personViewModel.personCreditsState.selectedDepartment == "Acting")
                                        stringResource(id = R.string.acting)
                                    else
                                        personViewModel.personCreditsState.selectedDepartment ?: "",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Column {
                                if (personViewModel.personCreditsState.selectedDepartment == "Acting") {
                                    personCredits.cast?.let { acting ->
                                        acting.values.forEachIndexed { index, castInfos ->
                                            castInfos?.forEach { info ->
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .clip(MaterialTheme.shapes.small)
                                                        .clickable {
                                                            when (info.mediaType) {
                                                                MediaType.TV, MediaType.MOVIE -> {
                                                                    onNavigateTo(RootNavGraph.DETAIL + "/${info.mediaType.name.lowercase()}/${info.id}")
                                                                }
                                                                MediaType.PERSON -> {
                                                                    onNavigateTo(RootNavGraph.PERSON + "/${info.name ?: ""}/${info.id}")
                                                                }
                                                                else -> {
                                                                    /*TODO: Navigate to lost your way screen*/
                                                                }
                                                            }
                                                        }
                                                        .padding(horizontal = 2.dp, vertical = 4.dp),
                                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        modifier = Modifier
                                                            .weight(.15f)
                                                            .fillMaxWidth(),
                                                        text = info.releaseDate?.year?.toString() ?: stringResource(id = R.string.dash),
                                                        style = MaterialTheme.typography.bodyMedium,
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                        textAlign = TextAlign.Center
                                                    )
                                                    Icon(
                                                        modifier = Modifier
                                                            .weight(.1f)
                                                            .size(12.dp),
                                                        painter = painterResource(id = R.drawable.icon_fiber_manual_record_fill0_wght400),
                                                        contentDescription = null,
                                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                                    )
                                                    AsyncImage(
                                                        modifier = Modifier
                                                            .height(80.dp)
                                                            .aspectRatio(0.675f)
                                                            .clip(MaterialTheme.shapes.small),
                                                        model = C.TMDB_IMAGES_BASE_URL + C.POSTER_W154 + info.posterPath,
                                                        placeholder = painterResource(id = R.drawable.placeholder),
                                                        error = painterResource(id = R.drawable.placeholder),
                                                        contentDescription = "Poster",
                                                        contentScale = ContentScale.Crop
                                                    )
                                                    Column(
                                                        modifier = Modifier.weight(1f, false)
                                                    ) {
                                                        info.name?.let { name ->
                                                            Text(
                                                                text = name,
                                                                style = MaterialTheme.typography.titleMedium
                                                            )
                                                        }
                                                        formatPersonActing(info.episodeCount, info.character).let { annotatedString ->
                                                            if (annotatedString.isNotEmpty()) {
                                                                Text(
                                                                    text = annotatedString,
                                                                    style = MaterialTheme.typography.bodyMedium,
                                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            if (index < acting.values.size - 1) {
                                                HorizontalDivider(
                                                    modifier = Modifier
                                                        .fillMaxWidth(.5f)
                                                        .align(Alignment.CenterHorizontally)
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
}

@Composable
fun PersonStarring(
    onNavigateTo: (route: String) -> Unit
) {

}
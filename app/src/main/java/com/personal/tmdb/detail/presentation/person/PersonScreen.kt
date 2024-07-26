package com.personal.tmdb.detail.presentation.person

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.shimmerEffect
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
                    if (personViewModel.personState.isLoading) {
                        Text(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.small)
                                .shimmerEffect(),
                            text = "Person's name",
                            color = Color.Transparent
                        )
                    } else {
                        personViewModel.personState.personInfo?.name?.let { name ->
                            Text(
                                text = name,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
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
            modifier = Modifier.padding(innerPadding)
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
            }
        }
    }
}
package com.personal.tmdb.detail.presentation.image

import android.app.Activity
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.OverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.util.ApplySystemBarsTheme
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.applySystemBarsTheme
import com.personal.tmdb.core.util.findActivity
import com.personal.tmdb.core.util.negativeHorizontalPadding
import com.personal.tmdb.core.util.shareText
import com.personal.tmdb.detail.domain.util.ImageType
import com.personal.tmdb.ui.theme.onSurfaceDark
import com.personal.tmdb.ui.theme.surfaceVariantDark
import kotlinx.coroutines.launch
import net.engawapg.lib.zoomable.ScrollGesturePropagation
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@Composable
fun ImageViewerScreenRoot(
    onNavigateBack: () -> Unit,
    preferencesState: () -> PreferencesState,
    viewModel: ImageViewerViewModel = hiltViewModel()
) {
    val imagesState by viewModel.imagesState.collectAsStateWithLifecycle()
    ImageViewerScreen(
        imagesState = { imagesState },
        preferencesState = preferencesState,
        imageViewerUiEvent = { event ->
            when (event) {
                ImageViewerUiEvent.OnNavigateBack -> onNavigateBack()
                else -> Unit
            }
            viewModel.imageViewerUiEvent(event)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun ImageViewerScreen(
    imagesState: () -> ImagesState,
    preferencesState: () -> PreferencesState,
    imageViewerUiEvent: (ImageViewerUiEvent) -> Unit
) {
    ApplySystemBarsTheme(applyLightStatusBars = true)
    val horizontalPagerState = rememberPagerState(
        pageCount = { imagesState().imagesByType?.size ?: 0 },
        initialPage = imagesState().initialPage ?: 0
    )
    val lazyGridState = rememberLazyGridState()
    val scope = rememberCoroutineScope()
    val view = LocalView.current
    val context = LocalContext.current
    val activity = context.findActivity()
    val darkTheme = isSystemInDarkTheme()
    DisposableEffect(Unit) {
        onDispose {
            applySystemBarsTheme(view, context, preferencesState().darkTheme ?: darkTheme)
        }
    }
    BackHandler {
        onBackAction(activity, imagesState(), imageViewerUiEvent)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.scrim)
    ) {
        imagesState().imagesByType?.let { images ->
            AnimatedContent(
                targetState = imagesState().showGridView,
                label = "Image viewer animation",
                transitionSpec = {
                    scaleIn(initialScale = .7f) + fadeIn() togetherWith scaleOut(targetScale = .7f) + fadeOut()
                }
            ) { targetState ->
                if (targetState) {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .safeDrawingPadding()
                            .padding(top = TopAppBarDefaults.TopAppBarExpandedHeight),
                        state = lazyGridState,
                        columns = GridCells.Adaptive(80.dp),
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        if (!imagesState().state?.backdrops.isNullOrEmpty() && !imagesState().state?.posters.isNullOrEmpty()) {
                            item(
                                span = { GridItemSpan(maxLineSpan) }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .negativeHorizontalPadding((-12).dp)
                                        .padding(bottom = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TextButton(
                                        onClick = { imageViewerUiEvent(ImageViewerUiEvent.SetImageType(ImageType.POSTERS)) },
                                        colors = ButtonDefaults.textButtonColors(
                                            contentColor = if (imagesState().imageType == ImageType.POSTERS) onSurfaceDark else surfaceVariantDark
                                        )
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.posters),
                                            fontWeight = if (imagesState().imageType == ImageType.POSTERS) FontWeight.Medium else FontWeight.Normal,
                                            fontSize = if (imagesState().imageType == ImageType.POSTERS) 18.sp else 16.sp
                                        )
                                    }
                                    TextButton(
                                        onClick = { imageViewerUiEvent(ImageViewerUiEvent.SetImageType(ImageType.BACKDROPS)) },
                                        colors = ButtonDefaults.textButtonColors(
                                            contentColor = if (imagesState().imageType == ImageType.BACKDROPS) onSurfaceDark else surfaceVariantDark
                                        )
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.backdrops),
                                            fontWeight = if (imagesState().imageType == ImageType.BACKDROPS) FontWeight.Medium else FontWeight.Normal,
                                            fontSize = if (imagesState().imageType == ImageType.BACKDROPS) 18.sp else 16.sp
                                        )
                                    }
                                }
                            }
                        }
                        itemsIndexed(
                            items = images,
                            key = { index, _ -> index }
                        ) { index, image ->
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .aspectRatio(1f)
                                    .clip(MaterialTheme.shapes.extraSmall)
                                    .clickable {
                                        imageViewerUiEvent(ImageViewerUiEvent.ChangeShowGridViewState)
                                        scope.launch {
                                            horizontalPagerState.scrollToPage(index)
                                        }
                                    },
                                model = C.TMDB_IMAGES_BASE_URL + C.POSTER_W300 + image?.filePath,
                                contentDescription = "Image",
                                placeholder = painterResource(id = R.drawable.placeholder),
                                error = painterResource(id = R.drawable.placeholder),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                } else {
                    CompositionLocalProvider(
                        LocalOverscrollConfiguration provides if (Build.VERSION.SDK_INT > 30) OverscrollConfiguration() else null
                    ) {
                        HorizontalPager(
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.Center),
                            state = horizontalPagerState,
                            beyondViewportPageCount = 2,
                            pageSize = PageSize.Fill
                        ) { page ->
                            val zoomState = rememberZoomState(maxScale = 4f)
                            AsyncImage(
                                modifier = Modifier
                                    .navigationBarsPadding()
                                    .fillMaxSize()
                                    .graphicsLayer {
                                        val endOffset = (
                                                (horizontalPagerState.currentPage - page) + horizontalPagerState.currentPageOffsetFraction
                                        ).coerceAtMost(0f)
                                        alpha = 1f + endOffset
                                        val scale = 1f + (endOffset * .2f)
                                        scaleX = scale
                                        scaleY = scale
                                    }
                                    .zoomable(
                                        zoomState = zoomState,
                                        enableOneFingerZoom = false,
                                        scrollGesturePropagation = ScrollGesturePropagation.ContentEdge,
                                        onTap = {
                                            if (imagesState().hideUi) {
                                                showSystemBars(activity)
                                            } else {
                                                hideSystemBars(activity)
                                            }
                                            imageViewerUiEvent(ImageViewerUiEvent.ChangeHideUiState)
                                        }
                                    ),
                                model = C.TMDB_IMAGES_BASE_URL + C.ORIGINAL + images[page]?.filePath,
                                onSuccess = { state ->
                                    zoomState.setContentSize(state.painter.intrinsicSize)
                                },
                                contentDescription = "Image"
                            )
                            val isVisible = page == horizontalPagerState.settledPage
                            LaunchedEffect(key1 = isVisible) {
                                if (!isVisible) {
                                    zoomState.reset()
                                }
                            }
                        }
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = !imagesState().hideUi,
            enter = slideInVertically(animationSpec = spring(stiffness = Spring.StiffnessLow)) + fadeIn(animationSpec = tween(delayMillis = 50)),
            exit = slideOutVertically(animationSpec = spring(stiffness = Spring.StiffnessLow)) + fadeOut(animationSpec = tween(durationMillis = 250))
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TopAppBar(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.scrim.copy(.5f))
                        .statusBarsPadding(),
                    title = {
                        AnimatedVisibility(
                            visible = !imagesState().showGridView,
                            enter = expandVertically() + fadeIn(),
                            exit = shrinkVertically() + fadeOut()
                        ) {
                            Text(
                                text = stringResource(
                                    id = when (imagesState().imageType) {
                                        ImageType.PROFILES -> R.string.profiles
                                        ImageType.STILLS -> R.string.stills
                                        ImageType.BACKDROPS -> R.string.backdrops
                                        ImageType.POSTERS -> R.string.posters
                                        ImageType.UNKNOWN -> R.string.empty
                                    }
                                ),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBackAction(activity, imagesState(), imageViewerUiEvent)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    actions = {
                        AnimatedVisibility(
                            visible = !imagesState().showGridView,
                            enter = expandVertically() + fadeIn(),
                            exit = shrinkVertically() + fadeOut()
                        ) {
                            Row {
                                IconButton(
                                    onClick = {
                                        context.shareText(C.SHARE_IMAGE.format(imagesState().imagesByType?.get(horizontalPagerState.currentPage)?.filePath))
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Share,
                                        contentDescription = "Share"
                                    )
                                }
                                IconButton(
                                    onClick = {/*TODO: Save to the gallery*/}
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_download_fill1_wght400),
                                        contentDescription = "Download"
                                    )
                                }
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        navigationIconContentColor = onSurfaceDark,
                        titleContentColor = onSurfaceDark,
                        actionIconContentColor = onSurfaceDark
                    )
                )
                androidx.compose.animation.AnimatedVisibility(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    visible = !imagesState().showGridView && horizontalPagerState.pageCount != 0,
                    enter = slideInVertically() + fadeIn(animationSpec = tween(delayMillis = 50)),
                    exit = slideOutVertically() + fadeOut(animationSpec = tween(durationMillis = 250))
                ) {
                    Row(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.scrim.copy(.5f))
                            .clickable(
                                interactionSource = null,
                                indication = null
                            ) {
                                imageViewerUiEvent(ImageViewerUiEvent.ChangeShowGridViewState)
                            }
                            .padding(start = 8.dp, top = 2.dp, end = 4.dp, bottom = 2.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = stringResource(
                                id = R.string.image_viewer_count,
                                (horizontalPagerState.currentPage + 1),
                                horizontalPagerState.pageCount
                            ),
                            style = MaterialTheme.typography.titleMedium,
                            color = onSurfaceDark
                        )
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = "Dropdown",
                            tint = onSurfaceDark
                        )
                    }
                }
            }
        }
    }
}

private fun onBackAction(
    activity: Activity,
    imagesState: ImagesState,
    imageViewerUiEvent: (ImageViewerUiEvent) -> Unit
) {
    when {
        imagesState.showGridView -> {
            if (imagesState.initialPage == null) imageViewerUiEvent(ImageViewerUiEvent.OnNavigateBack) else
                imageViewerUiEvent(ImageViewerUiEvent.ChangeShowGridViewState)
        }
        imagesState.hideUi -> {
            showSystemBars(activity)
            if (imagesState.initialPage == null) {
                imageViewerUiEvent(ImageViewerUiEvent.ChangeHideUiState)
                imageViewerUiEvent(ImageViewerUiEvent.ChangeShowGridViewState)
            } else{
                imageViewerUiEvent(ImageViewerUiEvent.OnNavigateBack)
            }
        }
        imagesState.initialPage == null -> imageViewerUiEvent(ImageViewerUiEvent.ChangeShowGridViewState)
        else -> imageViewerUiEvent(ImageViewerUiEvent.OnNavigateBack)
    }
}

private fun showSystemBars(activity: Activity) {
    val windowInsetsController = WindowCompat.getInsetsController(activity.window, activity.window.decorView)
    windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
}

private fun hideSystemBars(activity: Activity) {
    val windowInsetsController = WindowCompat.getInsetsController(activity.window, activity.window.decorView)
    windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
}
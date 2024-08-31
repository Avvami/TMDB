package com.personal.tmdb.core.presentation.image

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.personal.tmdb.MainActivity
import com.personal.tmdb.R
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.findActivity
import com.personal.tmdb.core.util.shareText
import com.personal.tmdb.ui.theme.backgroundLight
import com.personal.tmdb.ui.theme.scrimLight

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ImageViewerScreen(
    navigateBack: () -> Unit,
    imageViewerViewModel: ImageViewerViewModel = hiltViewModel()
) {
    val horizontalPagerState = rememberPagerState(
        pageCount = { 50 }
    )
    val activity = LocalContext.current.findActivity() as MainActivity
    val windowInsetsController = WindowCompat.getInsetsController(activity.window, activity.window.decorView)
    BackHandler {
        if (imageViewerViewModel.showGridView) {
            imageViewerViewModel.imageViewerUiEvent(ImageViewerUiEvent.ChangeShowGridView)
            return@BackHandler
        }
        if (imageViewerViewModel.hideUi) {
            windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
            navigateBack()
            return@BackHandler
        }
        navigateBack()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(scrimLight)
            .navigationBarsPadding()
    ) {
        AnimatedContent(
            targetState = imageViewerViewModel.showGridView,
            label = "Image viewer animation",
            transitionSpec = {
                scaleIn(initialScale = .7f) + fadeIn() togetherWith scaleOut(targetScale = .7f) + fadeOut()
            }
        ) { targetState ->
            if (targetState) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(top = 64.dp),/*TODO: Update the hardcoded value when Material 1.3.0 release*/
                    columns = GridCells.Adaptive(80.dp),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    items(
                        count = 50
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .aspectRatio(1f)
                                .clip(MaterialTheme.shapes.extraSmall)
                                .clickable {
                                    imageViewerViewModel.imageViewerUiEvent(ImageViewerUiEvent.ChangeShowGridView)
                                },
                            model = C.TMDB_IMAGES_BASE_URL + C.PROFILE_W185 + "/ghNt7Sqqve1SpLUoRUxAoZHLkJW.jpg",
                            contentDescription = "Image",
                            placeholder = painterResource(id = R.drawable.placeholder),
                            error = painterResource(id = R.drawable.placeholder),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            } else {
                CompositionLocalProvider(
                    LocalOverscrollConfiguration provides null
                ) {
                    HorizontalPager(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        state = horizontalPagerState,
                        beyondViewportPageCount = 2,
                        pageSize = PageSize.Fill
                    ) { page ->
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .graphicsLayer {
                                    val endOffset = (
                                            (horizontalPagerState.currentPage - page) + horizontalPagerState
                                                .currentPageOffsetFraction
                                            ).coerceAtMost(0f)
                                    alpha = 1f + endOffset
                                    val scale = 1f + (endOffset * .2f)
                                    scaleX = scale
                                    scaleY = scale
                                }
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    imageViewerViewModel.imageViewerUiEvent(ImageViewerUiEvent.ChangeHideUi)
                                    if (imageViewerViewModel.hideUi) {
                                        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
                                    } else {
                                        windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
                                    }
                                },
                            model = C.TMDB_IMAGES_BASE_URL + C.ORIGINAL + "/ghNt7Sqqve1SpLUoRUxAoZHLkJW.jpg",
                            contentDescription = "Image",
                            placeholder = painterResource(id = R.drawable.placeholder),
                            error = painterResource(id = R.drawable.placeholder)
                        )
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = !imageViewerViewModel.hideUi,
            enter = slideInVertically(animationSpec = spring(stiffness = Spring.StiffnessLow)) + fadeIn(animationSpec = tween(delayMillis = 50)),
            exit = slideOutVertically(animationSpec = spring(stiffness = Spring.StiffnessLow)) + fadeOut(animationSpec = tween(durationMillis = 250))
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TopAppBar(
                    modifier = Modifier
                        .background(scrimLight.copy(.5f))
                        .statusBarsPadding(),
                    title = {
                        Text(
                            text = "Profiles",
                            fontWeight = FontWeight.Medium
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                if (imageViewerViewModel.showGridView) {
                                    imageViewerViewModel.imageViewerUiEvent(ImageViewerUiEvent.ChangeShowGridView)
                                } else {
                                    navigateBack()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = "Back",
                                tint = backgroundLight
                            )
                        }
                    },
                    actions = {
                        val context = LocalContext.current
                        IconButton(
                            onClick = {
                                context.shareText(C.SHARE_IMAGE.format("/xxYawgFO1woBRveH7WL9D1BxB4W.jpg"))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Share,
                                contentDescription = "Share",
                                tint = backgroundLight
                            )
                        }
                        IconButton(
                            onClick = {/*TODO: Save to the gallery*/}
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_download_fill1_wght400),
                                contentDescription = "Download",
                                tint = backgroundLight
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        navigationIconContentColor = backgroundLight,
                        titleContentColor = backgroundLight,
                        actionIconContentColor = backgroundLight
                    )
                )
                androidx.compose.animation.AnimatedVisibility(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    visible = !imageViewerViewModel.showGridView,
                    enter = slideInVertically() + fadeIn(animationSpec = tween(delayMillis = 50)),
                    exit = slideOutVertically() + fadeOut(animationSpec = tween(durationMillis = 250))
                ) {
                    Text(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(scrimLight.copy(.5f))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                imageViewerViewModel.imageViewerUiEvent(ImageViewerUiEvent.ChangeShowGridView)
                            }
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        text = stringResource(
                            id = R.string.image_viewer_count,
                            (horizontalPagerState.currentPage + 1),
                            horizontalPagerState.pageCount
                        ),
                        style = MaterialTheme.typography.labelLarge,
                        color = backgroundLight
                    )
                }
            }
        }
    }
}
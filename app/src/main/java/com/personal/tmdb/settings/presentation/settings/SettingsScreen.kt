package com.personal.tmdb.settings.presentation.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.personal.tmdb.BuildConfig
import com.personal.tmdb.R
import com.personal.tmdb.UiEvent
import com.personal.tmdb.UserState
import com.personal.tmdb.core.domain.models.DropdownItem
import com.personal.tmdb.core.navigation.RootNavGraph
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.CustomDropdownMenu
import com.personal.tmdb.core.util.ApplyStatusBarsTheme
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.applyStatusBarsTheme
import com.personal.tmdb.ui.theme.backgroundLight
import com.personal.tmdb.ui.theme.outlineVariantLight
import com.personal.tmdb.ui.theme.tmdbLightPurple
import com.personal.tmdb.ui.theme.tmdbProfile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navigateBack: () -> Unit,
    onNavigateTo: (route: String) -> Unit,
    preferencesState: State<PreferencesState>,
    userState: State<UserState>,
    uiEvent: (UiEvent) -> Unit
) {
    var dropDownExpanded by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            if (!userState.value.sessionId.isNullOrEmpty()) {
                ApplyStatusBarsTheme(applyLightStatusBars = true)
                val view = LocalView.current
                val context = LocalContext.current
                val darkTheme = isSystemInDarkTheme()
                DisposableEffect(key1 = Unit) {
                    onDispose {
                        applyStatusBarsTheme(view, context, preferencesState.value.darkTheme ?: darkTheme)
                    }
                }
            }
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = if (userState.value.sessionId.isNullOrEmpty()) MaterialTheme.colorScheme.onBackground else backgroundLight,
                    actionIconContentColor = if (userState.value.sessionId.isNullOrEmpty()) MaterialTheme.colorScheme.onBackground else backgroundLight
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
                },
                actions = {
                    if (!userState.value.sessionId.isNullOrEmpty()) {
                        IconButton(
                            onClick = { dropDownExpanded = true }
                        )  {
                            Icon(
                                imageVector = Icons.Rounded.MoreVert,
                                contentDescription = "More"
                            )
                            CustomDropdownMenu(
                                expanded = dropDownExpanded,
                                onDismissRequest = { dropDownExpanded = false },
                                dropDownItems = listOf(
                                    DropdownItem(
                                        selected = true,
                                        iconRes = R.drawable.icon_format_paint_fill0_wght400,
                                        textRes = R.string.change_cover_color,
                                        onItemClick = {
                                            dropDownExpanded = false
                                        }
                                    ),
                                    DropdownItem(
                                        selected = true,
                                        iconRes = R.drawable.icon_logout_fill0_wght400,
                                        textRes = R.string.logout,
                                        onItemClick = {
                                            dropDownExpanded = false
                                        }
                                    )
                                )
                            )
                        }
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (!userState.value.sessionId.isNullOrEmpty()) {
                item {
                    val radialGradient by remember {
                        mutableStateOf(
                            object : ShaderBrush() {
                                override fun createShader(size: Size): Shader {
                                    val biggerDimension = maxOf(size.height, size.width)
                                    return RadialGradientShader(
                                        colors = listOf(tmdbLightPurple.copy(alpha = .3f), tmdbLightPurple.copy(alpha = 0f)),
                                        center = Offset(size.width, size.height),
                                        radius = biggerDimension
                                    )
                                }
                            }
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(tmdbProfile)
                            .background(radialGradient)
                            .height(IntrinsicSize.Min)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                modifier = Modifier.matchParentSize(),
                                painter = painterResource(id = R.drawable.pipes_pink),
                                contentDescription = "Profile Background",
                                contentScale = ContentScale.Crop,
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp,
                                    top = innerPadding.calculateTopPadding(),
                                    end = 16.dp,
                                    bottom = 16.dp
                                ),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape)
                                ) {
                                    AsyncImage(
                                        modifier = Modifier.fillMaxSize(),
                                        model = if (userState.value.userInfo?.tmdbAvatarPath == null)
                                            C.GRAVATAR_IMAGES_BASE_URL.format(userState.value.userInfo?.gravatarAvatarPath)
                                        else
                                            C.TMDB_IMAGES_BASE_URL + C.PROFILE_W185 + userState.value.userInfo?.tmdbAvatarPath,
                                        placeholder = painterResource(id = R.drawable.placeholder),
                                        error = painterResource(id = R.drawable.placeholder),
                                        contentDescription = "Avatar",
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    if (userState.value.userInfo?.name.isNullOrEmpty()) {
                                        Text(
                                            text = userState.value.userInfo?.username ?: "",
                                            style = MaterialTheme.typography.titleLarge,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                            fontWeight = FontWeight.Medium,
                                            color = backgroundLight
                                        )
                                    } else {
                                        Text(
                                            text = userState.value.userInfo?.name ?: "",
                                            style = MaterialTheme.typography.titleLarge,
                                            fontWeight = FontWeight.Medium,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                            color = backgroundLight
                                        )
                                        Text(
                                            text = userState.value.userInfo?.username ?: "",
                                            style = MaterialTheme.typography.bodyMedium,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                            color = outlineVariantLight
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                item {
                    Column(
                        modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerLow)
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                            text = stringResource(id = R.string.account),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { /*TODO: Navigate to my lists*/ }
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_event_list_fill0_wght400),
                                contentDescription = stringResource(id = R.string.lists),
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = stringResource(id = R.string.lists),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 52.dp),
                            color = MaterialTheme.colorScheme.background
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { /*TODO: Navigate to watchlist*/ }
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_bookmarks_fill0_wght400),
                                contentDescription = stringResource(id = R.string.watchlist),
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = stringResource(id = R.string.watchlist),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 52.dp),
                            color = MaterialTheme.colorScheme.background
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { /*TODO: Navigate to favorite*/ }
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_favorite_fill0_wght400),
                                contentDescription = stringResource(id = R.string.favorite),
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = stringResource(id = R.string.favorite),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 52.dp),
                            color = MaterialTheme.colorScheme.background
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { /*TODO: Navigate to recommendations*/ }
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_approval_delegation_fill0_wght400),
                                contentDescription = stringResource(id = R.string.recommendations),
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = stringResource(id = R.string.recommendations),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .then(
                            if (userState.value.sessionId.isNullOrEmpty()) {
                                Modifier.padding(top = innerPadding.calculateTopPadding())
                            } else {
                                Modifier
                            }
                        )
                        .background(MaterialTheme.colorScheme.surfaceContainerLow)
                ) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                        text = stringResource(id = R.string.settings),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /*TODO: Navigate to language*/ }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_language_korean_latin_fill0_wght400),
                            contentDescription = stringResource(id = R.string.language),
                            tint = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            text = stringResource(id = R.string.language),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(start = 52.dp),
                        color = MaterialTheme.colorScheme.background
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateTo(RootNavGraph.APPEARANCE) }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_palette_fill0_wght400),
                            contentDescription = stringResource(id = R.string.appearance),
                            tint = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            text = stringResource(id = R.string.appearance),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerLow)
                ) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                        text = stringResource(id = R.string.help),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /*TODO: Open support forums link*/ }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_forum_fill0_wght400),
                            contentDescription = stringResource(id = R.string.support_forums),
                            tint = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            text = stringResource(id = R.string.support_forums),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(start = 52.dp),
                        color = MaterialTheme.colorScheme.background
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /*TODO: Open privacy policy link*/ }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_policy_fill0_wght400),
                            contentDescription = stringResource(id = R.string.privacy_policy),
                            tint = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            text = stringResource(id = R.string.privacy_policy),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    text = stringResource(
                        id = R.string.app_version,
                        BuildConfig.VERSION_NAME,
                        BuildConfig.VERSION_CODE
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
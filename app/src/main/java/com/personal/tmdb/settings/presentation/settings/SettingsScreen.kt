package com.personal.tmdb.settings.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.personal.tmdb.BuildConfig
import com.personal.tmdb.R
import com.personal.tmdb.UiEvent
import com.personal.tmdb.ui.theme.tmdbLightPurple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navigateBack: () -> Unit,
    onNavigateTo: (route: String) -> Unit,
    modifier: Modifier = Modifier,
    uiEvent: (UiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
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
                },
                actions = {
                    IconButton(
                        onClick = { /*TODO: Drop down*/ }
                    )  {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = "More"
                        )
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                ) {
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
                                    .background(MaterialTheme.colorScheme.surfaceContainerHighest),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "U",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Column(
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = "Username",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = "Member since date",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.outlineVariant
                                )
                            }
                        }
                    }
                }
            }
            item {
                Column {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = stringResource(id = R.string.account),
                        style = MaterialTheme.typography.titleMedium
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
                            contentDescription = stringResource(id = R.string.watchlist)
                        )
                        Text(
                            text = stringResource(id = R.string.watchlist),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(start = 52.dp), thickness = .5.dp)
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
                            contentDescription = stringResource(id = R.string.favorite)
                        )
                        Text(
                            text = stringResource(id = R.string.favorite),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(start = 52.dp), thickness = .5.dp)
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
                            contentDescription = stringResource(id = R.string.recommendations)
                        )
                        Text(
                            text = stringResource(id = R.string.recommendations),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            item {
                Column {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = stringResource(id = R.string.settings),
                        style = MaterialTheme.typography.titleMedium
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
                            contentDescription = stringResource(id = R.string.language)
                        )
                        Text(
                            text = stringResource(id = R.string.language),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(start = 52.dp), thickness = .5.dp)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /*TODO: Navigate to appearance*/ }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_palette_fill0_wght400),
                            contentDescription = stringResource(id = R.string.appearance)
                        )
                        Text(
                            text = stringResource(id = R.string.appearance),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            item {
                Column {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = stringResource(id = R.string.help),
                        style = MaterialTheme.typography.titleMedium
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
                            contentDescription = stringResource(id = R.string.support_forums)
                        )
                        Text(
                            text = stringResource(id = R.string.support_forums),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(start = 52.dp), thickness = .5.dp)
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
                            contentDescription = stringResource(id = R.string.privacy_policy)
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
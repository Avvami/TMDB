package com.personal.tmdb.profile.presentation.watchlist

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.core.domain.util.negativeHorizontalPadding
import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.core.presentation.components.MediaGrid
import com.personal.tmdb.profile.presentation.watchlist.components.WatchlistFilterChips

@Composable
fun WatchlistScreenRoot(
    bottomPadding: Dp,
    canNavigateBack: Boolean = true,
    onNavigateBack: () -> Unit,
    onNavigateTo: (route: Route) -> Unit
) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistScreen(
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean,
    watchlistUiEvent: (WatchlistUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.watchlist))
                },
                navigationIcon = {
                    if (canNavigateBack) {
                        IconButton(
                            onClick = { watchlistUiEvent(WatchlistUiEvent.OnNavigateBack) }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = "Go back"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) { innerPadding ->
        MediaGrid(
            modifier = modifier.padding(top = innerPadding.calculateTopPadding()),
            contentPadding = PaddingValues(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp),
            span = {
                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    WatchlistFilterChips(
                        modifier = Modifier
                            .negativeHorizontalPadding((-16).dp)
                            .padding(bottom = 8.dp)
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp),
                        watchlistUiEvent = watchlistUiEvent
                    )
                }
            },
            items = {

            }
        )
    }
}
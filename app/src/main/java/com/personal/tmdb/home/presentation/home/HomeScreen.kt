package com.personal.tmdb.home.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.UiEvent
import com.personal.tmdb.core.navigation.RootNavGraph
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.util.ApplySystemBarsTheme
import com.personal.tmdb.home.presentation.home.components.modal.HomeModalDrawer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateTo: (route: String) -> Unit,
    preferencesState: State<PreferencesState>,
    uiEvent: (UiEvent) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ApplySystemBarsTheme(applyLightStatusBars = preferencesState.value.isDark)
    ModalNavigationDrawer(
        drawerContent = {
            HomeModalDrawer(
                drawerState = drawerState,
                closeDrawer = { scope.launch { drawerState.close() } },
                navigateToAuthScreen = { onNavigateTo(RootNavGraph.AUTH) },
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
                        navigationIconContentColor = MaterialTheme.colorScheme.onBackground
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
                    }
                )
            },
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ) { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding
            ) {
                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SuggestionChip(
                            onClick = { /*TODO*/ },
                            label = {
                                Text(text = stringResource(id = R.string.tv_shows))
                            }
                        )
                        SuggestionChip(
                            onClick = { /*TODO*/ },
                            label = {
                                Text(text = stringResource(id = R.string.movies))
                            }
                        )
                        SuggestionChip(
                            onClick = { /*TODO*/ },
                            label = {
                                Text(text = stringResource(id = R.string.people))
                            }
                        )
                    }
                }
            }
        }
    }
}
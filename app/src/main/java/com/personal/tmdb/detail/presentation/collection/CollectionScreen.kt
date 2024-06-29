package com.personal.tmdb.detail.presentation.collection

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.personal.tmdb.core.util.shimmerEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onNavigateTo: (route: String) -> Unit,
    collectionViewModel: CollectionViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (collectionViewModel.collectionState.isLoading) {
                        Text(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.small)
                                .shimmerEffect(),
                            text = "Collection title",
                            color = Color.Transparent
                        )
                    } else {
                        collectionViewModel.collectionState.collectionInfo?.name?.let { name ->
                            Text(text = name)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
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
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) { innerPadding ->

    }
}
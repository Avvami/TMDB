package com.personal.tmdb.detail.presentation.reviews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.CustomIconButton
import com.personal.tmdb.detail.presentation.reviews.components.Review

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsScreen(
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    preferencesState: State<PreferencesState>,
    reviewsViewModel: ReviewsViewModel = hiltViewModel()
) {
    val lazyGridState = rememberLazyStaggeredGridState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.reviews),
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                ),
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
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) { innerPadding ->
        LazyVerticalStaggeredGrid(
            modifier = Modifier.padding(innerPadding),
            state = lazyGridState,
            columns = StaggeredGridCells.Adaptive(300.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            reviewsViewModel.reviewsState.reviews?.results?.let { reviews ->
                items(
                    items = reviews,
                    key = { it.id }
                ) { review ->
                    Review(
                        review = review,
                        preferencesState = preferencesState
                    )
                }
            }
        }
    }
}
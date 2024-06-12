package com.personal.tmdb.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun MediaContentListView(
//    mediaItems: MediaWrapper,
    isCardsListView: Boolean = false,
    showPostersTitle: Boolean = false,
    postersCount: Int = 3
) {
    if (isCardsListView) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
//            mediaItems.tvSeries?.let { series ->
//                items(
//                    count = series.size,
//                    key = { series[it].id }
//                ) { index ->
//                    val item = series[index]
//                    MediaCard(
//                        posterPath = item.posterPath,
//                        title = item.name,
//                        releaseDate = item.firstAirDate,
//                        overview = item.overview
//                    )
//                }
//            }
//            mediaItems.movies?.let { movies ->
//                items(
//                    count = movies.size,
//                    key = { movies[it].id }
//                ) { index ->
//                    val item = movies[index]
//                    MediaCard(
//                        posterPath = item.posterPath,
//                        title = item.title,
//                        releaseDate = item.releaseDate,
//                        overview = item.overview
//                    )
//                }
//            }
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(postersCount),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
//            mediaItems.tvSeries?.let { series ->
//                items(
//                    count = series.size,
//                    key = { series[it].id }
//                ) { index ->
//                    val item = series[index]
//                    MediaPoster(
//                        posterPath = item.posterPath,
//                        title = if (showPostersTitle) item.name else null
//                    )
//                }
//            }
//            mediaItems.movies?.let { movies ->
//                items(
//                    count = movies.size,
//                    key = { movies[it].id }
//                ) { index ->
//                    val item = movies[index]
//                    MediaPoster(
//                        posterPath = item.posterPath,
//                        title = if (showPostersTitle) item.title else null
//                    )
//                }
//            }
        }
    }
}
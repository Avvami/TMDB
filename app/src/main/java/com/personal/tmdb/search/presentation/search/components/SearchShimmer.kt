package com.personal.tmdb.search.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.components.MediaPosterShimmer
import com.personal.tmdb.core.util.shimmerEffect

@Composable
fun SearchShimmer(
    modifier: Modifier = Modifier,
    showTitle: Boolean
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            Row(
                modifier = Modifier.clip(MaterialTheme.shapes.small).shimmerEffect().padding(16.dp, 6.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.all),
                    color = Color.Transparent
                )
                Text(
                    text = stringResource(id = R.string.movies),
                    color = Color.Transparent
                )
                Text(
                    text = stringResource(id = R.string.people),
                    color = Color.Transparent
                )
            }
        }
        items(
            count = 15
        ) {
            MediaPosterShimmer(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.675f)
                    .clip(RoundedCornerShape(18.dp))
                    .shimmerEffect(),
                showTitle = showTitle,
            )
        }
    }
}
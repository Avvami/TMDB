package com.personal.tmdb.detail.presentation.reviews.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.core.util.shimmerEffect

@Composable
fun ReviewsScreenShimmer(
    showReview: Boolean
) {
    if (showReview) {
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraSmall)
                        .shimmerEffect(),
                    text = "Written by author on Jan 1, 1111",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Transparent
                )
                Row(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(MaterialTheme.shapes.extraSmall)
                            .shimmerEffect(),
                        painter = painterResource(id = R.drawable.icon_thumbs_up_down_fill1_wght400),
                        contentDescription = "Thumbs",
                        tint = Color.Transparent
                    )
                    Text(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.extraSmall)
                            .shimmerEffect(),
                        text = "10",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Transparent
                    )
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.extraSmall)
                    .shimmerEffect(),
                text = "",
                style = MaterialTheme.typography.bodyLarge,
                minLines = 15
            )
        }
    } else {
        LazyVerticalStaggeredGrid(
            userScrollEnabled = false,
            columns = StaggeredGridCells.Adaptive(300.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalItemSpacing = 16.dp,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                count = 5
            ) {
                ReviewShimmer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .shimmerEffect()
                        .padding(16.dp)
                )
            }
        }
    }
}
package com.personal.tmdb.detail.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.core.util.shimmerEffect

@Composable
fun DetailScreenShimmer() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .shimmerEffect(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = stringResource(id = R.string.tmdb),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .shimmerEffect(),
                    text = "Media title",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color.Transparent
                )
                Text(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .shimmerEffect(),
                    text = "Media release date, genres, runtime",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Transparent
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .shimmerEffect()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .shimmerEffect(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .sizeIn(minWidth = 32.dp, minHeight = 32.dp)
                                .clip(MaterialTheme.shapes.small)
                        )
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = "0.0",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Color.Transparent
                                )
                                Icon(
                                    modifier = Modifier.size(14.dp),
                                    painter = painterResource(id = R.drawable.icon_bar_chart_fill0_wght400),
                                    contentDescription = null,
                                    tint = Color.Transparent
                                )
                            }
                        }
                    }
                }
            }
            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect()
                    .padding(10.dp),
                text = "One last hunt to save us all.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Transparent
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect(),
                text = "",
                style = MaterialTheme.typography.bodyLarge,
                minLines = 4
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect(),
            ) {
                Text(
                    text = stringResource(id = R.string.starring),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Transparent
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect(),
            ) {
                Text(
                    text = stringResource(id = R.string.genres),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Transparent
                )
            }
        }
    }
}
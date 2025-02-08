package com.personal.tmdb.detail.presentation.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.core.domain.util.C
import com.personal.tmdb.detail.domain.models.CollectionInfo
import com.personal.tmdb.ui.theme.surfaceLight

@Composable
fun DetailCollection(
    modifier: Modifier = Modifier,
    collectionInfo: () -> CollectionInfo
) {
    Box(
        modifier = modifier
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .matchParentSize(),
            model = C.TMDB_IMAGES_BASE_URL + C.BACKDROP_W1280 + collectionInfo().backdropPath,
            contentDescription = "Backdrop",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.scrim.copy(alpha = .6f))
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(id = R.string.part_of_collection, collectionInfo().name ?: ""),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                color = surfaceLight
            )
            collectionInfo().parts?.let { partInfos ->
                Text(
                    text = stringResource(id = R.string.includes, partInfos.joinToString(", ") { it.name.toString() }),
                    style = MaterialTheme.typography.bodyMedium,
                    color = surfaceLight,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}